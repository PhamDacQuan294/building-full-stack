import ClientBuildingListPage from "@/pages/client/buildings";
import ClientBuildingDetailPage from "@/pages/client/buildings/detail";

const buildingRoutes = [
  {
    path: "buildings",
    element: <ClientBuildingListPage />,
  },
   {
    path: "buildings/:id",
    element: <ClientBuildingDetailPage />,
  },
];

export default buildingRoutes;