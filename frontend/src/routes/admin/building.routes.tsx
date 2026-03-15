import Building from "@/pages/admin/Buildings";
import CreateBuilding from "@/pages/admin/Buildings/CreateBuilding";
import EditBuilding from "@/pages/admin/Buildings/EditBuilding";

const buildingRoutes = [
  {
    path: "buildings",
    element: <Building />,
  },
  {
    path: "buildings/create",
    element: <CreateBuilding />,
  },
  {
    path: "buildings/:id/edit",
    element: <EditBuilding />,
  },
];

export default buildingRoutes;