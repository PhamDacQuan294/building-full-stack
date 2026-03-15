export type TransactionItem = {
  id: number;
  customerId: number;
  customerName: string;
  staffId: number;
  staffName: string;
  transactionType: string;
  transactionStatus: string;
  content: string;
  transactionDate: string;
};

export type TransactionFormData = {
  customerId: string;
  staffId: string;
  transactionType: string;
  transactionStatus: string;
  content: string;
  transactionDate: string;
};

export type TransactionFilters = {
  customerId: string;
  staffId: string;
  transactionType: string;
  transactionStatus: string;
  fromDate: string;
  toDate: string;
  page: number;
  limit: number;
};