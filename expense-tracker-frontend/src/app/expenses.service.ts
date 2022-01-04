import { Injectable } from '@angular/core';

class ExpenseCategory {
  id: string;
  name: string;

  constructor(id: string, name: string) {
    this.id = id;
    this.name = name;
  }
}

class ShopIdentifier {
  id: string;
  name: string;

  constructor(id: string, name: string) {
    this.id = id;
    this.name = name;
  }
}

class PurchaseRecord {
  id: string;
  shop: ShopIdentifier;
  date: Date;
  name: string;
  desc: string;
  price: number;
  amount: number;

  constructor(id: string, shop: ShopIdentifier, date: Date, name: string, desc: string, price: number, amount: number) {
    this.id = id;
    this.shop = shop;
    this.date = date;
    this.name = name;
    this.desc = desc;
    this.price = price;
    this.amount = amount;
  }
}

@Injectable({
  providedIn: 'root'
})
export class ExpensesService {

  constructor() { }

  getCategories(): ExpenseCategory[] {
    return [];
  }

  addCategory(category: string): void {

  }

  getShops(): ShopIdentifier[] {
    return [];
  }

  addShop(shop: string): void {

  }

  getAutocompleteForPurchaseName(phrase: string, shopId?: string): string[] {
    return [];
  }

  addNewPurchases(purchases: PurchaseRecord[]): void {

  }
}
