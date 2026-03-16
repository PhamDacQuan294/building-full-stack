import ClientBuildingListPage from "@/pages/client/buildings";
import ClientBuildingDetailPage from "@/pages/client/buildings/detail";
import ClientFavoritePage from "@/pages/client/favorites";

const buildingRoutes = [
  {
    path: "buildings",
    element: <ClientBuildingListPage />,
  },
  {
    path: "buildings/:id",
    element: <ClientBuildingDetailPage />,
  },
  {
    path: "/favorites",
    element: <ClientFavoritePage />,
  },
];

export default buildingRoutes;
