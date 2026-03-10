import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Checkbox } from "@/components/ui/checkbox";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";

import { useBuildingStore } from "@/stores/admin/useBuildingStore";
import { formatVND } from "@/utils/priceFormatter";
import { buildingService } from "@/services/admin/buildingService";
import { toast } from "sonner";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { useState } from "react";
import { UserCheck } from "lucide-react";
import AssignBuildingDialog from "@/components/admin/buildings/AssignBuildingDialog";

export default function BuildingTable() {
  const { items, loading, pagination, setFilters, search } = useBuildingStore();
  const [selectedIds, setSelectedIds] = useState<Array<string | number>>([]);
  const [bulkStatus, setBulkStatus] = useState("");
  const [openAssign, setOpenAssign] = useState(false)
  const [selectedBuildingId, setSelectedBuildingId] = useState<string | number | null>(null)

  const handleOpenAssign = (id: string | number) => {
    setSelectedBuildingId(id)
    setOpenAssign(true)
  }

  const goToPage = async (page: number) => {
    setFilters({ page });
    await search();
  };

  const handleToggleStatus = async (
    id: string | number,
    currentStatus: string,
  ) => {
    try {
      const nextStatus = currentStatus === "ACTIVE" ? "INACTIVE" : "ACTIVE";

      await buildingService.changeStatus(id, nextStatus);
      await search();

      toast.success("Cập nhật trạng thái thành công!");
    } catch (error) {
      console.error(error);
      toast.error("Cập nhật trạng thái thất bại!");
    }
  };

  const handleSelectOne = (id: string | number, checked: boolean) => {
    if (checked) {
      setSelectedIds((prev) => [...prev, id]);
    } else {
      setSelectedIds((prev) => prev.filter((item) => item !== id));
    }
  };

  const handleSelectAll = (checked: boolean) => {
    if (checked) {
      setSelectedIds(items.map((item) => item.id));
    } else {
      setSelectedIds([]);
    }
  };

  const handleApplyBulkStatus = async () => {
    try {
      if (!bulkStatus) {
        toast.error("Vui lòng chọn trạng thái muốn áp dụng!");
        return;
      }

      if (selectedIds.length === 0) {
        toast.error("Vui lòng chọn ít nhất một toà nhà!");
        return;
      }

      await buildingService.changeMultiStatus(selectedIds, bulkStatus);

      toast.success("Cập nhật trạng thái nhiều toà nhà thành công!");

      setSelectedIds([]);
      setBulkStatus("");

      await search();
    } catch (error) {
      console.error(error);
      toast.error("Cập nhật trạng thái thất bại!");
    }
  };

  return (
    <Card className="rounded-xl border-border">
      <CardHeader>
        <CardTitle className="text-base">Danh sách toà nhà</CardTitle>
      </CardHeader>

      <CardContent>
        <div className="flex flex-wrap items-end gap-3 mb-4">
          <div className="w-[220px]">
            <Select value={bulkStatus} onValueChange={setBulkStatus}>
              <SelectTrigger>
                <SelectValue placeholder="Chọn trạng thái..." />
              </SelectTrigger>
              <SelectContent className="border-border">
                <SelectItem value="ACTIVE">Hoạt động</SelectItem>
                <SelectItem value="INACTIVE">Dừng hoạt động</SelectItem>
              </SelectContent>
            </Select>
          </div>

          <Button onClick={handleApplyBulkStatus}>Áp dụng</Button>
        </div>

        <div className="overflow-x-auto border rounded-lg border-border">
          <Table className="w-full table-fixed">
            <TableHeader>
              <TableRow className="bg-muted/40 border-border">
                <TableHead className="w-[40px]">
                  <div className="flex justify-center">
                    <Checkbox
                      checked={
                        items.length > 0 && selectedIds.length === items.length
                      }
                      onCheckedChange={(checked) => handleSelectAll(!!checked)}
                    />
                  </div>
                </TableHead>

                <TableHead className="w-[70px] text-center">STT</TableHead>

                <TableHead className="w-[100px]">Tên toà nhà</TableHead>

                <TableHead className="w-[320px]">Địa điểm</TableHead>

                <TableHead className="w-[140px] text-center">
                  Diện tích sàn
                </TableHead>

                <TableHead className="w-[150px] text-center">
                  Giá thuê
                </TableHead>

                <TableHead className="w-[140px] text-center">
                  Trạng thái
                </TableHead>

                <TableHead className="w-[350px] text-center">
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
                  <TableRow key={item.id} className="border-border">
                    <TableCell>
                      <div className="flex justify-center">
                        <Checkbox
                          checked={selectedIds.includes(item.id)}
                          onCheckedChange={(checked) =>
                            handleSelectOne(item.id, !!checked)
                          }
                        />
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

                    <TableCell className="text-center">
                      {item.rentPrice ? `${formatVND(item.rentPrice)}` : "-"}
                    </TableCell>

                    <TableCell className="text-center">
                      {item.status === "ACTIVE" ? (
                        <Badge
                          className="cursor-pointer bg-primary text-primary-foreground hover:bg-primary w-[80px]"
                          onClick={() =>
                            handleToggleStatus(item.id, item.status!)
                          }
                        >
                          Hoạt động
                        </Badge>
                      ) : (
                        <Badge
                          variant="destructive"
                          className="cursor-pointer w-[80px]"
                          onClick={() =>
                            handleToggleStatus(item.id, item.status!)
                          }
                        >
                          Dừng
                        </Badge>
                      )}
                    </TableCell>

                    <TableCell>
                      <div className="flex items-center gap-2">
                        <Button
                          size="sm"
                          className="text-white bg-yellow-500 hover:bg-yellow-600"
                        >
                          Sửa
                        </Button>

                        <Button
                          size="sm"
                          className="text-white bg-red-500 hover:bg-red-600"
                        >
                          Xoá
                        </Button>

                        <Button
                          size="sm"
                          className="text-white bg-blue-500 hover:bg-blue-600"
                        >
                          Chi tiết
                        </Button>
                        <Button
                          variant="outline"
                          className="gap-2 border-border"
                          onClick={() => handleOpenAssign(item.id)}
                        >
                          <UserCheck className="w-4 h-4" />
                          Giao toà nhà
                        </Button>
                      </div>
                    </TableCell>
                  </TableRow>
                ))
              )}
            </TableBody>
          </Table>
        <AssignBuildingDialog open={openAssign} onOpenChange={setOpenAssign} buildingId={selectedBuildingId}/>
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
  );
}
