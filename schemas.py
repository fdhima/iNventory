from typing import Optional
from pydantic import BaseModel
from datetime import datetime


class ProductBase(BaseModel):
    name: str


class ProductCreate(ProductBase):
    pass


class Product(ProductBase):
    id: int
    created_at: Optional[datetime]
    updated_at: Optional[datetime]

    class Config:
        orm_mode = True


class ItemBase(BaseModel):
    product_id: Optional[int]


class ItemCreate(ItemBase):
    pass


class Item(ItemBase):
    id: int
    created_at: Optional[datetime]
    updated_at: Optional[datetime]

    class Config:
        orm_mode = True


class UserBase(BaseModel):
    username: str


class UserCreate(UserBase):
    hashed_password: str


class User(UserBase):
    id: int
    created_at: Optional[datetime]
    updated_at: Optional[datetime]

    class Config:
        orm_mode = True
