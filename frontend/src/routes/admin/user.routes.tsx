import UserList from "@/pages/admin/Users/UserList";
import CreateUser from "@/pages/admin/Users/CreateUser";

const userRoutes = [
  {
    path: "users",
    element: <UserList />,
  },
  {
    path: "users/create",
    element: <CreateUser />,
  },
];

export default userRoutes;