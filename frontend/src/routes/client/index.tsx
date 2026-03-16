import authClientRoutes from "./auth.route";
import buildingRoutes from "./buildings.route";
import homeRoutes from "./home.route";

const clientRoutes = [
  ...authClientRoutes,
  
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