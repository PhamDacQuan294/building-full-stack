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

export default function BuildingFilterSort() {
  return (
    <Card className="rounded-xl border-border">
      <CardHeader>
        <CardTitle className="text-base">Bộ lọc & Sắp xếp</CardTitle>
      </CardHeader>

      <CardContent className="flex flex-wrap items-end gap-3">
        {/* Filter trạng thái */}
        <div className="flex flex-wrap gap-2">
          <Button size="sm" variant="default" className="border-border">
            Tất cả
          </Button>
          <Button size="sm" variant="outline" className="border-border">
            Hoạt động
          </Button>
          <Button size="sm" variant="outline" className="border-border">
            Dừng hoạt động
          </Button>
        </div>

        {/* Sort */}
        <div className="flex items-end gap-2 ml-auto">
          <div className="w-56">
            <Label className="block mb-2 text-xs text-muted-foreground">
              Sắp xếp
            </Label>

            <Select>
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

          <Button variant="destructive">Clear</Button>
        </div>
      </CardContent>
    </Card>
  )
}