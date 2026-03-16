import UserList from "@/pages/admin/Users/UserList";
import CreateUser from "@/pages/admin/Users/CreateUser";
import EditUser from "@/pages/admin/Users/EditUser";

const userRoutes = [
  {
    path: "users",
    element: <UserList />,
  },
  {
    path: "users/create",
    element: <CreateUser />,
  },
  {
    path: "users/:id/edit",
    element: <EditUser />
  }
];

export default userRoutes;