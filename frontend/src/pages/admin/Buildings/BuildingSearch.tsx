import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { useBuildingStore } from "@/stores/admin/useBuildingStore";

export default function BuildingSearch() {
  const {
    filters,
    setFilters,
    setTypes,
    search,
    resetFilters,
    loading,
    districts,
    staffs,
    rentTypes,
  } = useBuildingStore();

  return (
    <Card className="rounded-xl border-border">
      <CardHeader>
        <CardTitle className="text-base">Tìm kiếm</CardTitle>
      </CardHeader>

      <CardContent className="space-y-4">
        <div className="grid gap-4 lg:grid-cols-3 md:grid-cols-2 sm:grid-cols-1">
          <div className="space-y-2">
            <Label>Tên Toà Nhà</Label>
            <Input
              placeholder="Nhập tên..."
              value={filters.name}
              onChange={(e) => setFilters({ name: e.target.value })}
              className="w-[250px] lg:w-[200px] md:w-[180px]"
            />
          </div>

          <div className="space-y-2">
            <Label>Diện Tích Sàn</Label>
            <Input
              placeholder="VD: 1000"
              value={filters.floorArea}
              onChange={(e) => setFilters({ floorArea: e.target.value })}
              className="w-[250px] lg:w-[200px] md:w-[180px]"
            />
          </div>

          <div className="space-y-2">
            <Label>Quận / Huyện</Label>
            <Select
              value={filters.district}
              onValueChange={(v) => setFilters({ district: v })}
            >
              <SelectTrigger className="w-[250px] lg:w-[200px] md:w-[180px]">
                <SelectValue placeholder="-- Chọn Quận --" />
              </SelectTrigger>
              <SelectContent className="border-border">
                {Object.entries(districts).map(([code, name]) => (
                  <SelectItem key={code} value={code}>
                    {name}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>

          <div className="space-y-2">
            <Label>Phường</Label>
            <Input
              placeholder="VD: Hoà Xuân"
              value={filters.ward}
              onChange={(e) => setFilters({ ward: e.target.value })}
              className="w-[250px] lg:w-[200px] md:w-[180px]"
            />
          </div>

          <div className="space-y-2">
            <Label>Đường</Label>
            <Input
              placeholder="VD: Nguyễn Trãi"
              value={filters.street}
              onChange={(e) => setFilters({ street: e.target.value })}
              className="w-[250px] lg:w-[200px] md:w-[180px]"
            />
          </div>

          <div className="space-y-2">
            <Label>Số tầng hầm</Label>
            <Input
              type="number"
              placeholder="VD: 2"
              value={filters.basement}
              onChange={(e) => setFilters({ basement: e.target.value })}
              className="w-[250px] lg:w-[200px] md:w-[180px]"
            />
          </div>

          <div className="space-y-2">
            <Label>Giá thuê từ</Label>
            <Input
              type="number"
              placeholder="VD: 10"
              value={filters.rentFrom}
              onChange={(e) => setFilters({ rentFrom: e.target.value })}
              className="w-[250px] lg:w-[200px] md:w-[180px]"
            />
          </div>

          <div className="space-y-2">
            <Label>Giá thuê đến</Label>
            <Input
              type="number"
              placeholder="VD: 30"
              value={filters.rentTo}
              onChange={(e) => setFilters({ rentTo: e.target.value })}
              className="w-[250px] lg:w-[200px] md:w-[180px]"
            />
          </div>

          <div className="space-y-2">
            <Label>Nhân viên phụ trách</Label>
            <Select
              value={filters.staffId}
              onValueChange={(v) => setFilters({ staffId: v })}
            >
              <SelectTrigger className="w-[250px] lg:w-[200px] md:w-[180px]">
                <SelectValue placeholder="-- Chọn nhân viên --" />
              </SelectTrigger>
              <SelectContent className="border-border">
                {Object.entries(staffs).map(([key, name]) => (
                  <SelectItem key={key} value={key}>
                    {name}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>
        </div>

        <div className="flex flex-wrap items-center gap-6">
          {Object.entries(rentTypes).map(([key, label]) => (
            <label key={key} className="flex items-center gap-2">
              <Checkbox
                checked={!!filters.types[key]}
                onCheckedChange={(v) => setTypes({ [key]: !!v })}
              />
              <span className="text-sm">{label}</span>
            </label>
          ))}
        </div>

        <div className="flex gap-2">
          <Button onClick={search} disabled={loading}>
            {loading ? "Đang tìm..." : "Tìm kiếm"}
          </Button>

          <Button
            variant="outline"
            onClick={resetFilters}
            className="border-border"
          >
            Reset
          </Button>
        </div>
      </CardContent>
    </Card>
  );
}
