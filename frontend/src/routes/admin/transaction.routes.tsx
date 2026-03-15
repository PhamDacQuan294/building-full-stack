import TransactionList from "@/pages/admin/Transaction/TransactionList";
import CreateTransaction from "@/pages/admin/Transaction/CreateTransaction";
import EditTransaction from "@/pages/admin/Transaction/EditTransaction";

const transactionRoutes = [
  {
    path: "transactions",
    element: <TransactionList />,
  },
  {
    path: "transactions/create",
    element: <CreateTransaction />,
  },
  {
    path: "transactions/:id/edit",
    element: <EditTransaction />,
  },
];

export default transactionRoutes;