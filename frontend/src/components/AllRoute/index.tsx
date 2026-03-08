import routes from "@/routes/admin"
import { useRoutes } from "react-router-dom"

const AllRoute = () => {
  const elements = useRoutes(routes);

  return (
    <>
      {elements}
    </>
  )
}

export default AllRoute