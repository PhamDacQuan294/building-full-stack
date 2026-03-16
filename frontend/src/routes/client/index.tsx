import buildingRoutes from "./buildings.route";
import homeRoutes from "./home.route";

const clientRoutes = [
   {
    children: [
      {
        path: "/",
        children: [
          ...homeRoutes,
          ...buildingRoutes,
        ],
      },
    ],
  },
];

export default clientRoutes;