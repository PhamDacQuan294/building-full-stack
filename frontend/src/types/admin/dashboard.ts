export type ChartItem = {
  name: string;
  value: number;
};

export type DashboardData = {
  totalUsers: number;
  totalRoles: number;
  totalBuildings: number;
  totalCustomers: number;
  totalTransactions: number;

  activeBuildings: number;
  inactiveBuildings: number;

  newCustomersThisMonth: number;

  transactionStatusStats: ChartItem[];
  customerStatusStats: ChartItem[];
  monthlyTransactionStats: ChartItem[];
};