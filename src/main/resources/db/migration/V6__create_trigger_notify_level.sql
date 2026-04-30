CREATE OR REPLACE FUNCTION notify_low_stock()
RETURNS trigger AS $$
DECLARE
  v_product_name TEXT;
BEGIN
  IF NEW.level < NEW.threshold THEN

    -- Fetch product name via JOIN on the products table
    SELECT name INTO v_product_name
    FROM products
    WHERE id = NEW.product_id;

    PERFORM pg_notify(
      'low_stock_alert',
      json_build_object(
        'id',           NEW.id,
        'product_id',   NEW.product_id,
        'product_name', COALESCE(v_product_name, 'Unknown Product'),
        'location',     NEW.location,
        'level',        NEW.level,
        'threshold',    NEW.threshold,
        'updated_at',   NEW.updated_at
      )::text
    );

  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Attach trigger to your table
CREATE TRIGGER stock_level_check
AFTER INSERT OR UPDATE OF level ON stock_levels  -- only fires when `level` changes
FOR EACH ROW EXECUTE FUNCTION notify_low_stock();