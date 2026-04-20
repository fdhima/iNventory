# Inventory Management System — Requirements

Project purpose
- Provide a backend API to track products, inventory levels, orders, and operational tasks; send low-stock notifications via messaging (RabbitMQ).

Scope
- Inventory CRUD, stock management and restocking flows
- Order processing that updates inventory
- Task operations for warehouse/operations users (CRUD + filters)
- Event notifications for low-stock and order events

Actors
- Admin: manage products, view reports
- Warehouse/Operations user: create/complete tasks, view tasks
- External system / consumer: subscribes to RabbitMQ events

Functional requirements
- Products
	- Create product with name, sku, description, category, unit, reorder_threshold.
	- Update product metadata and reorder thresholds.
	- Retrieve product list and individual product by ID/SKU.

- Inventory / Stock
	- Increase/decrease stock levels (manual adjustments, restock, returns).
	- Reserve stock when processing orders; prevent oversell.
	- Track available vs reserved quantities.
	- Emit LowStockEvent when quantity <= reorder_threshold.

- Orders
	- Create order with line items referencing products and quantities.
	- Validate availability and reserve/decrement stock on confirmation.
	- Support cancel/modify with appropriate stock adjustments.
	- Provide order status history / audit trail.

- Task operations (CRUD)
	- Create task: title, description, optional category, related product/order IDs, due date, priority.
	- Read tasks: list tasks for authenticated user, filter by status (pending/completed), category, product, date range.
	- Retrieve single task by ID.
	- Update task: edit fields, mark complete/incomplete.
	- Delete task.

- Events & integrations
	- Publish events to RabbitMQ for key actions: LowStockEvent, OrderPlaced, OrderCancelled, StockAdjusted.
	- Events must contain sufficient context (IDs, quantities, timestamps) for consumers to act.

Non-functional requirements
- API
	- RESTful JSON API with clear resource URIs and status codes.
	- Pagination for list endpoints; predictable sorting and filtering.

- Persistence
	- Use a reliable relational database (Postgres recommended) for inventory consistency and transactions.

- Consistency & reliability
	- Order processing and stock updates must be transactional to avoid oversell.
	- Idempotent event publishing where appropriate.

- Security
	- Authenticate API requests (e.g., JWT or token-based auth).
	- Authorize actions by role (admin vs ops user).
	- Input validation and rate limiting on critical endpoints.

- Observability
	- Structured logging for key actions (orders, stock changes, events).
	- Basic metrics: orders/sec, low-stock events, failed events.

- Performance & scale
	- Support concurrent order traffic with correct locking/transactions to avoid race conditions.

Data model (high level)
- Product: id, sku, name, description, category, unit, reorder_threshold, created_at, updated_at
- Inventory (per product/location): product_id, location_id, quantity, reserved_quantity
- Order: id, user_id, status, total_amount, created_at, updated_at
- OrderLine: order_id, product_id, quantity, unit_price
- Task: id, user_id, title, description, category, related_id, status, priority, due_date, created_at, updated_at

API endpoints (examples)
- Products
	- GET /products
	- POST /products
	- GET /products/{id}
	- PUT /products/{id}
	- DELETE /products/{id}

- Inventory
	- POST /inventory/adjust (product_id, delta, reason)
	- GET /inventory/{product_id}

- Orders
	- POST /orders (create + reserve/confirm)
	- GET /orders/{id}
	- PUT /orders/{id} (modify/cancel)

- Tasks
	- POST /tasks
	- GET /tasks (filters: status, category, product_id)
	- GET /tasks/{id}
	- PUT /tasks/{id}
	- DELETE /tasks/{id}

Acceptance criteria
- Core flows implemented and verified with tests: product CRUD, inventory adjust, order create/complete/cancel, task CRUD.
- Low-stock events are published to RabbitMQ when thresholds are crossed.
- Order processing prevents overselling under concurrent requests.

Developer setup (minimum)
- Install Python deps: `pip install -r requirements.txt`.
- Configure DB connection and RabbitMQ URL via env vars.
- Run migrations and start the API server (details in project docs).

Notes / next steps
- Define exact schema/migrations and API request/response JSON contracts.
- Add integration tests for order+inventory transactional behavior and event publication.
