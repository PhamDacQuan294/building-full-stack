// src/pages/admin/Buildings/BuildingFilterSort.tsx
import { Button } from "@/components/ui/button"
import { Label } from "@/components/ui/label"
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { useBuildingStore } from "@/stores/admin/useBuildingStore"
import type { BuildingStatus } from "@/types/admin/buildings"

export default function BuildingFilterSort() {
  const { filters, setFilters, search, resetFilters } = useBuildingStore();

  const handleFilter = async (status: BuildingStatus) => {
    setFilters({ status, page: 1 })
    await search();
  }

  const handleSort = async (sort: string) => {
    setFilters({ sort, page: 1 })
    await search();
  }

  return (
    <Card className="rounded-xl border-border">
      <CardHeader>
        <CardTitle className="text-base">Bộ lọc & Sắp xếp</CardTitle>
      </CardHeader>

      <CardContent className="flex flex-wrap items-end gap-3">
        {/* Filter trạng thái */}
        <div className="flex flex-wrap gap-2">
          <Button 
            size="sm" 
            variant={filters.status === "all" ? "default" : "outline"}
            className="border-border"
            onClick={() => handleFilter("all")}
          >
            Tất cả
          </Button>
          <Button            
            size="sm" 
            variant={filters.status === "ACTIVE" ? "default" : "outline"} 
            className="border-border"
            onClick={() => handleFilter("ACTIVE")}
          >
            Hoạt động
          </Button>
          <Button 
            size="sm" 
            variant={filters.status === "INACTIVE" ? "default" : "outline"}
            className="border-border"
            onClick={() => handleFilter("INACTIVE")}
          >
            Dừng hoạt động
          </Button>
        </div>

        {/* Sort */}
        <div className="flex items-end gap-2 ml-auto">
          <div className="w-56">
            <Label className="block mb-2 text-xs text-muted-foreground">
              Sắp xếp
            </Label>

            <Select value={filters.sort} onValueChange={handleSort}>
              <SelectTrigger>
                <SelectValue placeholder="Chọn sắp xếp..." />
              </SelectTrigger>
              <SelectContent className="border-border">
                <SelectItem value="rent-desc">Giá thuê giảm dần</SelectItem>
                <SelectItem value="rent-asc">Giá thuê tăng dần</SelectItem>
                <SelectItem value="area-desc">Diện tích giảm dần</SelectItem>
                <SelectItem value="area-asc">Diện tích tăng dần</SelectItem>
                <SelectItem value="name-asc">Tên A - Z</SelectItem>
                <SelectItem value="name-desc">Tên Z - A</SelectItem>
              </SelectContent>
            </Select>
          </div>

          <Button variant="destructive" onClick={resetFilters}>Clear</Button>
        </div>
      </CardContent>
    </Card>
  )
}