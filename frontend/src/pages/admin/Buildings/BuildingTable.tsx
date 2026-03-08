import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Badge } from "@/components/ui/badge"
import { Checkbox } from "@/components/ui/checkbox"
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"

import { useBuildingStore } from "@/stores/admin/useBuildingStore"
import { formatVND } from "@/utils/priceFormatter"

export default function BuildingTable() {
  const { items, loading, pagination, setFilters, search } = useBuildingStore()

  const goToPage = async (page: number) => {
    setFilters({ page })
    await search()
  }

  return (
    <Card className="rounded-xl border-border">
      <CardHeader>
        <CardTitle className="text-base">Danh sách toà nhà</CardTitle>
      </CardHeader>

      <CardContent>

        {/* TABLE */}
        <div className="overflow-x-auto border rounded-lg border-border">
          <Table className="w-full table-fixed">

            <TableHeader>
              <TableRow className="bg-muted/40 border-border">

                <TableHead className="w-[52px]">
                  <div className="flex justify-center">
                    <Checkbox />
                  </div>
                </TableHead>

                <TableHead className="w-[70px] text-center">
                  STT
                </TableHead>

                <TableHead className="w-[200px]">
                  Tên toà nhà
                </TableHead>

                <TableHead className="w-[320px]">
                  Địa điểm
                </TableHead>

                <TableHead className="w-[140px] text-center">
                  Diện tích sàn
                </TableHead>

                <TableHead className="w-[150px] text-right">
                  Giá thuê
                </TableHead>

                <TableHead className="w-[140px] text-center">
                  Trạng thái
                </TableHead>

                <TableHead className="w-[180px] text-right">
                  Hành động
                </TableHead>

              </TableRow>
            </TableHeader>


            <TableBody>

              {loading ? (

                <TableRow>
                  <TableCell
                    colSpan={8}
                    className="py-8 text-center text-muted-foreground"
                  >
                    Đang tải dữ liệu...
                  </TableCell>
                </TableRow>

              ) : items.length === 0 ? (

                <TableRow>
                  <TableCell
                    colSpan={8}
                    className="py-8 text-center text-muted-foreground"
                  >
                    Không có dữ liệu
                  </TableCell>
                </TableRow>

              ) : (

                items.map((item, index) => (

                  <TableRow
                    key={item.id}
                    className="border-border"
                  >

                    <TableCell>
                      <div className="flex justify-center">
                        <Checkbox />
                      </div>
                    </TableCell>

                    <TableCell className="text-center">
                      {(pagination.page - 1) * 10 + (index + 1)}
                    </TableCell>

                    <TableCell className="font-medium truncate">
                      {item.name}
                    </TableCell>

                    <TableCell className="truncate text-muted-foreground">
                      {item.address || "-"}
                    </TableCell>

                    <TableCell className="text-center">
                      {item.floorArea ?? "-"}
                    </TableCell>

                    <TableCell className="text-right">
                      {item.rentPrice
                        ? `${formatVND(item.rentPrice)}đ`
                        : "-"}
                    </TableCell>

                    <TableCell className="text-center">
                      {item.status === "ACTIVE" ? (
                        <Badge className="bg-primary text-primary-foreground hover:bg-primary">
                          Hoạt động
                        </Badge>
                      ) : (
                        <Badge variant="destructive">
                          Dừng
                        </Badge>
                      )}
                    </TableCell>

                    <TableCell>
                      <div className="flex items-center justify-end gap-2">
                        <Button size="sm" variant="secondary">
                          Sửa
                        </Button>

                        <Button size="sm" variant="destructive">
                          Xoá
                        </Button>
                      </div>
                    </TableCell>

                  </TableRow>

                ))

              )}

            </TableBody>
          </Table>
        </div>

        <div className="flex items-center justify-end gap-2 mt-4">

          <Button
            size="sm"
            variant="outline"
            disabled={loading || pagination.page <= 1}
            onClick={() => goToPage(pagination.page - 1)}
          >
            Trang trước
          </Button>

          <Button
            size="sm"
            variant={pagination.page === 1 ? "default" : "outline"}
            disabled={loading}
            onClick={() => goToPage(1)}
          >
            1
          </Button>

          <Button
            size="sm"
            variant={pagination.page === 2 ? "default" : "outline"}
            disabled={loading || pagination.totalPage < 2}
            onClick={() => goToPage(2)}
          >
            2
          </Button>

          <Button
            size="sm"
            variant="outline"
            disabled={loading || pagination.page >= pagination.totalPage}
            onClick={() => goToPage(pagination.page + 1)}
          >
            Trang sau
          </Button>

        </div>

      </CardContent>
    </Card>
  )
}