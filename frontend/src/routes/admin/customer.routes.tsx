import CustomerList from "@/pages/admin/Customers/CustomerList";
import CreateCustomer from "@/pages/admin/Customers/CreateCustomer";
import EditCustomer from "@/pages/admin/Customers/EditCustomer";
import CustomerCareHistory from "@/pages/admin/Customers/CustomerCareHistory";

const customerRoutes = [
  {
    path: "customers",
    element: <CustomerList />,
  },
  {
    path: "customers/create",
    element: <CreateCustomer />,
  },
  {
    path: "customers/:id/edit",
    element: <EditCustomer />,
  },
  {
    path: "customers/:id/care-history",
    element: <CustomerCareHistory />,
  },
];

export default customerRoutes;