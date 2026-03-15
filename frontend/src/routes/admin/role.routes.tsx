import RoleList from "@/pages/admin/Roles/RoleList";
import CreateRole from "@/pages/admin/Roles/CreateRole";
import EditRole from "@/pages/admin/Roles/EditRole";

const roleRoutes = [
  {
    path: "roles",
    element: <RoleList />,
  },
  {
    path: "roles/create",
    element: <CreateRole />,
  },
  {
    path: "roles/:id/edit",
    element: <EditRole />,
  },
];

export default roleRoutes;