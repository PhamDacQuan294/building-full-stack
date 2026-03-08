import { Link } from "react-router-dom"
import { Button } from "@/components/ui/button"

import BuildingSearch from "./BuildingSearch"
import BuildingFilterSort from "./BuildingFilterSort"
import BuildingTable from "./BuildingTable"
import { useBuildingStore } from "@/stores/admin/useBuildingStore"
import { useEffect } from "react"

export default function BuildingsPage() {
  const { search } = useBuildingStore()

  useEffect(() => {
    search()
  }, [search])

  return (
    <div className="space-y-4">
      <div className="flex items-center justify-between">
        <h1 className="text-xl font-semibold">Danh sách toà nhà</h1>

        <Button asChild variant="outline" className="border-border">
          <Link to="/admin/buildings/create">+ Thêm mới</Link>
        </Button>
      </div>

      <BuildingSearch />
      <BuildingFilterSort />
      <BuildingTable />
    </div>
  )
}