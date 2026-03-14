import { Link } from "react-router-dom";
import { Button } from "@/components/ui/button";

import BuildingSearch from "./BuildingSearch";
import BuildingFilterSort from "./BuildingFilterSort";
import BuildingTable from "./BuildingTable";
import { useBuildingStore } from "@/stores/admin/useBuildingStore";
import { useEffect } from "react";
import { Building2, Lock, Plus } from "lucide-react";
import { getAuthoritiesFromToken } from "@/utils/auth";

export default function BuildingsPage() {
  const { search } = useBuildingStore();
  const authorities = getAuthoritiesFromToken();

  const canCreate = authorities.includes("BUILDING_CREATE");
  const canView = authorities.includes("BUILDING_VIEW");

  useEffect(() => {
    if (canView) {
      search();
    }
  }, [search, canView]);

  if (!canView) {
    return (
      <div className="p-6 bg-white border shadow-sm rounded-2xl">
        <div className="flex items-center gap-3 mb-3">
          <Lock className="w-5 h-5 text-red-500" />
          <h1 className="text-lg font-semibold">Bạn không có quyền xem toà nhà</h1>
        </div>

        <p className="text-sm text-slate-500">
          Tài khoản của bạn chưa được cấp quyền <b>BUILDING_VIEW</b>.
        </p>
      </div>
    );
  }

  return (
    <div className="space-y-4">
      <div className="flex items-center justify-between mb-4">
        <div className="flex items-center gap-2">
          <Building2 className="w-5 h-5 text-primary" />
          <h1 className="text-xl font-semibold">Danh sách toà nhà</h1>
        </div>

        <div className="flex items-center gap-2">
          {canCreate && (
            <Button asChild className="gap-2">
              <Link to="/admin/buildings/create">
                <Plus className="w-4 h-4" />
                Thêm mới
              </Link>
            </Button>
          )}
        </div>
      </div>

      <BuildingSearch />
      <BuildingFilterSort />
      <BuildingTable />
    </div>
  );
}