import CreateBuilding from "@/pages/admin/Buildings/CreateBuilding";
import EditBuilding from "@/pages/admin/Buildings/EditBuilding";

const buildingAdminRoutes = [
  {
    path: "buildings/create",
    element: <CreateBuilding />,
  },
  {
    path: "buildings/:id/edit",
    element: <EditBuilding />,
  },
];

export default buildingAdminRoutes;