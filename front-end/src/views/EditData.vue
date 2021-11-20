<template>
  <div class="container-fluid d-flex justify-content-center align-items-center" style="height: 92vh">
    <div class="container overflow-auto">
      <div class="row">
        <div class="col-sm">
          <div class="row border-bottom border-top p-1">
            <div class="col-sm text-left">
              <span>TIKET</span>
            </div>
            <div class="col-sm text-left">
              <span>: {{ recordData.ticket }}</span>
            </div>
          </div>
          <div class="row border-bottom p-1">
            <div class="col-sm text-left">
              <span>TANGGAL MASUK</span>
            </div>
            <div class="col-sm text-left d-flex justify-content-between">
              <span>: {{ recordData.date_in }}</span>
              <span @click="editDateIn()" class="text-primary ml-3 edit-span">Edit</span>
            </div>
          </div>
          <div class="row border-bottom p-1">
            <div class="col-sm text-left">
              <span>TANGGAL KELUAR</span>
            </div>
            <div class="col-sm text-left d-flex justify-content-between">
              <span>: {{ recordData.date_out }}</span>
              <span @click="editDateOut()" class="text-primary ml-3 edit-span">Edit</span>
            </div>
          </div>
          <div class="row border-bottom p-1">
            <div class="col-sm text-left">
              <span>NO. KENDARAAN</span>
            </div>
            <div class="col-sm text-left d-flex justify-content-between">
              <span class="text-uppercase">: {{ recordData.vehicle }}</span>
              <span @click="editVehicle()" class="text-primary ml-3 edit-span">Edit</span>
            </div>
          </div>
          <div class="row border-bottom p-1">
            <div class="col-sm text-left">
              <span>STATUS BARANG</span>
            </div>
            <div class="col-sm text-left">
              <span>: {{ recordData.product_status }}</span>
            </div>
          </div>
          <div class="row border-bottom p-1">
            <div class="col-sm text-left">
              <span>NAMA BARANG</span>
            </div>
            <div class="col-sm text-left">
              <span>: {{ recordData.product_name }}</span>
            </div>
          </div>
          <div v-if="recordData.customer != '-'" class="row border-bottom p-1">
            <div class="col-sm text-left">
              <span>KOSTUMER</span>
            </div>
            <div class="col-sm text-left">
              <span>: {{ recordData.customer }}</span>
            </div>
          </div>
          <div v-if="recordData.supplier != '-'" class="row border-bottom p-1">
            <div class="col-sm text-left">
              <span>SUPPLIER</span>
            </div>
            <div class="col-sm text-left">
              <span>: {{ recordData.supplier }}</span>
            </div>
          </div>
          <div class="row border-bottom p-1">
            <div class="col-sm text-left">
              <span>NO. ORDER</span>
            </div>
            <div class="col-sm text-left d-flex justify-content-between">
              <span class="text-uppercase">: {{ recordData.order_id }}</span>
              <span @click="editOrder()" class="text-primary ml-3 edit-span">Edit</span>
            </div>
          </div>
          <div class="row border-bottom p-1">
            <div class="col-sm text-left">
              <span>SUPIR</span>
            </div>
            <div class="col-sm text-left d-flex justify-content-between">
              <span class="text-uppercase">: {{ recordData.driver }}</span>
              <span @click="editDriver()" class="text-primary ml-3 edit-span">Edit</span>
            </div>
          </div>
          <div class="row border-bottom p-1">
            <div class="col-sm text-left">
              <span>OPERATOR</span>
            </div>
            <div class="col-sm text-left">
              <span>: {{ recordData.operator }}</span>
            </div>
          </div>
          <div class="row border-bottom p-1">
            <div class="col-sm text-left">
              <span>KETERANGAN</span>
            </div>
            <div class="col-sm text-left d-flex justify-content-between">
              <span class="text-uppercase">: {{ recordData.info }}</span>
              <span @click="editInfo()" class="text-primary ml-3 edit-span">Edit</span>
            </div>
          </div>
        </div>

        <div class="col-sm">
          <div class="row border-bottom border-top p-1">
            <div class="col-sm text-left">
              <span>BRUTO</span>
            </div>
            <div class="col-sm text-left d-flex justify-content-between">
              <span>: {{ new Intl.NumberFormat("id-ID").format(recordData.gross) }}</span>
              <span @click="editGross()" class="text-primary ml-3 edit-span">Edit</span>
            </div>
          </div>
          <div class="row border-bottom p-1">
            <div class="col-sm text-left">
              <span>TARA</span>
            </div>
            <div class="col-sm text-left d-flex justify-content-between">
              <span>: {{ new Intl.NumberFormat("id-ID").format(recordData.tare) }}</span>
              <span @click="editTare()" class="text-primary ml-3 edit-span">Edit</span>
            </div>
          </div>
          <div class="row border-bottom p-1">
            <div class="col-sm text-left">
              <span>HASIL</span>
            </div>
            <div class="col-sm text-left">
              <span>: {{ new Intl.NumberFormat("id-ID").format(recordData.result) }}</span>
            </div>
          </div>
          <div class="row border-bottom p-1">
            <div class="col-sm text-left">
              <span>POTONGAN</span>
            </div>
            <div class="col-sm text-left d-flex justify-content-between">
              <span>: {{ new Intl.NumberFormat("id-ID").format(recordData.sort) }}</span>
              <span v-if="recordData.tare != '0'" @click="editSort()" class="text-primary ml-3 edit-span">Edit</span>
            </div>
          </div>
          <div class="row border-bottom p-1">
            <div class="col-sm text-left">
              <span>PERSEN POTONGAN</span>
            </div>
            <div class="col-sm text-left">
              <span>: {{ recordData.percent }}%</span>
            </div>
          </div>
          <div class="row border-bottom p-1">
            <div class="col-sm text-left">
              <span>NETTO</span>
            </div>
            <div class="col-sm text-left">
              <span>: {{ new Intl.NumberFormat("id-ID").format(recordData.nett) }}</span>
            </div>
          </div>
          <div class="row border-bottom p-1">
            <div class="col-sm text-left">
              <span>HARGA</span>
            </div>
            <div class="col-sm text-left d-flex justify-content-between">
              <span>: {{ new Intl.NumberFormat("id-ID").format(recordData.price) }}</span>
              <span v-if="recordData.tare != '0'" @click="editPrice()" class="text-primary ml-3 edit-span">Edit</span>
            </div>
          </div>
          <div class="row border-bottom p-1">
            <div class="col-sm text-left">
              <span>TOTAL HARGA</span>
            </div>
            <div class="col-sm text-left">
              <span>: {{ new Intl.NumberFormat("id-ID").format(recordData.total_price) }}</span>
            </div>
          </div>
          <div class="row border-bottom p-1">
            <div class="col-sm text-left">
              <span>CASH</span>
            </div>
            <div class="col-sm text-left d-flex justify-content-between">
              <span>: {{ new Intl.NumberFormat("id-ID").format(recordData.cash) }}</span>
              <span @click="editCash()" class="text-primary ml-3 edit-span">Edit</span>
            </div>
          </div>
          <div class="row border-bottom p-1">
            <div class="col-sm text-left">
              <span>TRANSFER</span>
            </div>
            <div class="col-sm text-left d-flex justify-content-between">
              <span>: {{ new Intl.NumberFormat("id-ID").format(recordData.transfer) }}</span>
              <span @click="editTransfer()" class="text-primary ml-3 edit-span">Edit</span>
            </div>
          </div>
          <div class="row border-bottom p-1">
            <div class="col-sm text-left">
              <span>TIMBANGAN LUAR</span>
            </div>
            <div class="col-sm text-left d-flex justify-content-between">
              <span>: {{ new Intl.NumberFormat("id-ID").format(recordData.out_weight) }}</span>
              <span @click="editOutweight()" class="text-primary ml-3 edit-span">Edit</span>
            </div>
          </div>
        </div>
      </div>
      <div id="footer-button" class="container-fluid d-flex justify-content-between mt-3">
        <button @click="back()" class="btn btn-info m-1">Batal</button>
        <div>
          <button @click="deleteData(recordData.ticket)" class="btn btn-danger m-1">Hapus Data</button>
          <button @click="saveChange()" class="btn btn-success m-1">Simpan Perubahan</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { computed } from "@vue/reactivity";
import axios from "../services/axios";
export default {
  data() {
    return {
      recordData: [],
    };
  },

  methods: {
    back() {
      window.location.replace("/dashboard");
    },

    editDateIn() {
      let edited = "";
      this.$swal
        .fire({
          title: "Tanggal Masuk",
          showCloseButton: true,
          confirmButtonText: "Edit",
          html: "<input type='text' class='form-control text-uppercase' id='nameForEdit'>",
          preConfirm: () => {
            edited = this.$swal.getPopup().querySelector("#nameForEdit").value;
          },
        })
        .then((result) => {
          if (result.isConfirmed) {
            this.recordData.date_in = edited;
          }
        });
    },

    editDateOut() {
      let edited = "";
      this.$swal
        .fire({
          title: "Tanggal Keluar",
          showCloseButton: true,
          confirmButtonText: "Edit",
          html: "<input type='text' class='form-control text-uppercase' id='nameForEdit'>",
          preConfirm: () => {
            edited = this.$swal.getPopup().querySelector("#nameForEdit").value;
          },
        })
        .then((result) => {
          if (result.isConfirmed) {
            this.recordData.date_out = edited;
          }
        });
    },

    editVehicle() {
      let edited = "";
      this.$swal
        .fire({
          title: "No. Kendaraan",
          showCloseButton: true,
          confirmButtonText: "Edit",
          html: "<input type='text' class='form-control text-uppercase' id='nameForEdit'>",
          preConfirm: () => {
            edited = this.$swal.getPopup().querySelector("#nameForEdit").value;
          },
        })
        .then((result) => {
          if (result.isConfirmed) {
            this.recordData.vehicle = edited;
          }
        });
    },

    editOrder() {
      let edited = "";
      this.$swal
        .fire({
          title: "No. Order",
          showCloseButton: true,
          confirmButtonText: "Edit",
          html: "<input type='text' class='form-control text-uppercase' id='nameForEdit'>",
          preConfirm: () => {
            edited = this.$swal.getPopup().querySelector("#nameForEdit").value;
          },
        })
        .then((result) => {
          if (result.isConfirmed) {
            this.recordData.order_id = edited;
          }
        });
    },

    editDriver() {
      let edited = "";
      this.$swal
        .fire({
          title: "Supir",
          showCloseButton: true,
          confirmButtonText: "Edit",
          html: "<input type='text' class='form-control text-uppercase' id='nameForEdit'>",
          preConfirm: () => {
            edited = this.$swal.getPopup().querySelector("#nameForEdit").value;
          },
        })
        .then((result) => {
          if (result.isConfirmed) {
            this.recordData.driver = edited;
          }
        });
    },

    editInfo() {
      let edited = "";
      this.$swal
        .fire({
          title: "Keterangan",
          showCloseButton: true,
          confirmButtonText: "Edit",
          html: "<input type='text' class='form-control text-uppercase' id='nameForEdit'>",
          preConfirm: () => {
            edited = this.$swal.getPopup().querySelector("#nameForEdit").value;
          },
        })
        .then((result) => {
          if (result.isConfirmed) {
            this.recordData.info = edited;
          }
        });
    },

    editGross() {
      let edited = "";
      this.$swal
        .fire({
          title: "Bruto",
          showCloseButton: true,
          confirmButtonText: "Edit",
          html: "<input type='number' class='form-control' id='nameForEdit'>",
          preConfirm: () => {
            edited = this.$swal.getPopup().querySelector("#nameForEdit").value;
          },
        })
        .then((result) => {
          if (result.isConfirmed) {
            this.recordData.gross = edited;
            this.counting();
          }
        });
    },

    editTare() {
      let edited = "";
      this.$swal
        .fire({
          title: "Tara",
          showCloseButton: true,
          confirmButtonText: "Edit",
          html: "<input type='number' class='form-control' id='nameForEdit'>",
          preConfirm: () => {
            edited = this.$swal.getPopup().querySelector("#nameForEdit").value;
          },
        })
        .then((result) => {
          if (result.isConfirmed) {
            this.recordData.tare = edited;
            this.counting();
          }
        });
    },

    editSort() {
      let edited = "";
      this.$swal
        .fire({
          title: "Potongan",
          showCloseButton: true,
          confirmButtonText: "Edit",
          html: "<input type='number' class='form-control' id='nameForEdit'>",
          preConfirm: () => {
            edited = this.$swal.getPopup().querySelector("#nameForEdit").value;
          },
        })
        .then((result) => {
          if (result.isConfirmed) {
            this.recordData.sort = edited;
            this.counting();
          }
        });
    },

    editPrice() {
      let edited = "";
      this.$swal
        .fire({
          title: "Harga",
          showCloseButton: true,
          confirmButtonText: "Edit",
          html: "<input type='number' class='form-control' id='nameForEdit'>",
          preConfirm: () => {
            edited = this.$swal.getPopup().querySelector("#nameForEdit").value;
          },
        })
        .then((result) => {
          if (result.isConfirmed) {
            this.recordData.price = edited;
            this.counting();
          }
        });
    },

    editCash() {
      let edited = "";
      this.$swal
        .fire({
          title: "Cash",
          showCloseButton: true,
          confirmButtonText: "Edit",
          html: "<input type='number' class='form-control' id='nameForEdit'>",
          preConfirm: () => {
            edited = this.$swal.getPopup().querySelector("#nameForEdit").value;
          },
        })
        .then((result) => {
          if (result.isConfirmed) {
            this.recordData.cash = edited;
          }
        });
    },

    editTransfer() {
      let edited = "";
      this.$swal
        .fire({
          title: "Transfer",
          showCloseButton: true,
          confirmButtonText: "Edit",
          html: "<input type='number' class='form-control' id='nameForEdit'>",
          preConfirm: () => {
            edited = this.$swal.getPopup().querySelector("#nameForEdit").value;
          },
        })
        .then((result) => {
          if (result.isConfirmed) {
            this.recordData.transfer = edited;
          }
        });
    },

    editOutweight() {
      let edited = "";
      this.$swal
        .fire({
          title: "Timbangan Luar",
          showCloseButton: true,
          confirmButtonText: "Edit",
          html: "<input type='number' class='form-control' id='nameForEdit'>",
          preConfirm: () => {
            edited = this.$swal.getPopup().querySelector("#nameForEdit").value;
          },
        })
        .then((result) => {
          if (result.isConfirmed) {
            this.recordData.out_weight = edited;
          }
        });
    },

    counting() {
      const gross = this.recordData.gross;
      const tare = this.recordData.tare;
      const result = this.recordData.result;
      const sort = this.recordData.sort;
      //let percent = this.recordData.percent;
      //let nett = this.recordData.nett;

      this.recordData.result = gross - tare;
      if (tare != "0") {
        const per = (100 * sort) / result;
        this.recordData.percent = parseFloat(per).toFixed(2);
        this.recordData.nett = this.recordData.result - sort;
        this.recordData.total_price = this.recordData.nett * this.recordData.price;
      }
    },

    saveChange() {
      var val = this.recordData.total_price - this.recordData.cash - this.recordData.transfer;

      if (val == 0) {
        let gaps = 0;
        if (this.recordData.product_status == "MASUK") {
          gaps = this.recordData.nett - this.recordData.out_weight;
        } else if (this.recordData.product_status == "KELUAR") {
          gaps = this.recordData.out_weight - this.recordData.nett;
        }
        const model = {
          ticket: this.recordData.ticket,
          date_in: this.recordData.date_in,
          date_out: this.recordData.date_out,
          vehicle: this.recordData.vehicle,
          product_status: this.recordData.product_status,
          product_name: this.recordData.product_name,
          customer: this.recordData.customer,
          supplier: this.recordData.supplier,
          order_id: this.recordData.order_id,
          driver: this.recordData.driver,
          info: this.recordData.info,
          gross: this.recordData.gross,
          tare: this.recordData.tare,
          result: this.recordData.result,
          sort: this.recordData.sort,
          percent: this.recordData.percent,
          nett: this.recordData.nett,
          price: this.recordData.price,
          total_price: this.recordData.total_price,
          cash: this.recordData.cash,
          transfer: this.recordData.transfer,
          out_weight: this.recordData.out_weight,
          operator: this.recordData.operator,
          gap: gaps,
          sync: "update",
        };
        axios.post("edit-data", model).then((result) => {
          if (result.data.status == "success") {
            this.$swal.fire("success", result.data.messages, "success");
            this.back();
          } else {
            this.$swal.fire("error", result.data.messages, "error");
          }
        });
      } else {
        this.$swal.fire("error", "Nominal cash dan transfer tidak sesuai", "error");
      }
    },

    deleteData(value) {
      this.$swal
        .fire({
          title: "Yakin akan menghapus data ?",
          showCancelButton: true,
          confirmButtonText: "Hapus",
        })
        .then((result) => {
          if (result.isConfirmed) {
            let data = { ticket: value, sync: "delete" };
            axios.post("delete-data", data).then((result) => {
              if (result.data.status == "success") {
                this.$swal.fire("Deleted!", result.data.messages, "success");
                this.$router.push("/dashboard");
              } else {
                this.$swal.fire("Failed!", result.data.messages, "info");
              }
            });
          }
        });
    },
  },

  mounted() {
    this.recordData = computed(() => this.$store.state.editData);
  },
};
</script>

<style>
p {
  margin: 0px;
  padding: 0px;
}

.edit-span {
  cursor: pointer;
}

#footer-button button {
  min-width: 200px;
}
</style>
