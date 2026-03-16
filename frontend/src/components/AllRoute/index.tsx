import adminRoutes from "@/routes/admin";
import clientRoutes from "@/routes/client";
import { useRoutes } from "react-router-dom";

const AllRoute = () => {
  const elements = useRoutes([
    ...adminRoutes,
    ...clientRoutes,
  ]);

  return elements;
};

export default AllRoute;