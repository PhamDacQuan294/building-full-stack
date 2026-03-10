import { Link } from "react-router-dom";
import { Button } from "@/components/ui/button";

import BuildingSearch from "./BuildingSearch";
import BuildingFilterSort from "./BuildingFilterSort";
import BuildingTable from "./BuildingTable";
import { useBuildingStore } from "@/stores/admin/useBuildingStore";
import { useEffect } from "react";
import { Building2, Plus } from "lucide-react";

export default function BuildingsPage() {
  const { search } = useBuildingStore();
  useEffect(() => {
    search();
  }, [search]);

  return (
    <div className="space-y-4">
      <div className="flex items-center justify-between mb-4">
        <div className="flex items-center gap-2">
          <Building2 className="w-5 h-5 text-primary" />
          <h1 className="text-xl font-semibold">Danh sách toà nhà</h1>
        </div>

        <div className="flex items-center gap-2">
          <Button asChild className="gap-2">
            <Link to="/admin/buildings/create">
              <Plus className="w-4 h-4" />
              Thêm mới
            </Link>
          </Button>
        </div>
      </div>

      <BuildingSearch />
      <BuildingFilterSort />
      <BuildingTable />
    </div>
  );
}
