<template>
  <div class="container-fluid">
    <h5 class="text-center"><u>Data Timbangan</u></h5>
    <div class="container-fluid d-flex justify-content-between align-items-center mt-3 mb-3 p-0">
      <div>
        {{ showing }}
        <span v-if="showing != 'Menampilkan : Seluruh data.'" @click="resetForm()" class="badge bg-danger rounded-pill" style="cursor: pointer; font-weight: normal; font-style: normal">Reset</span>
      </div>
      <button type="button" class="btn btn-basic btn-sm" data-bs-toggle="modal" data-bs-target="#filterModal"><i class="fas fa-filter"></i> FILTER DATA</button>
    </div>
    <div class="row">
      <div class="table-responsive" style="max-height: 75vh">
        <table id="calibration-table" class="table table-bordered text-sm-center">
          <thead class="table-light" style="position: sticky; top: 0">
            <tr>
              <th class="table-head">Tiket</th>
              <th class="table-head">Tanggal</th>
              <th class="table-head">No. Truck</th>
              <th class="table-head">Status</th>
              <th class="table-head">Barang</th>
              <th class="table-head">Kostumer/supplier</th>
              <th class="table-head">Bruto</th>
              <th class="table-head">Tara</th>
              <th class="table-head">Netto</th>
              <th class="table-head">Harga</th>
              <th class="table-head">Total Harga</th>
              <th class="table-head">Action</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(calibration, index) in calibrations" :key="index">
              <td v-if="calibration.date_out == '-'" class="align-middle table-warning">
                {{ calibration.ticket }}
              </td>
              <td v-else class="align-middle">{{ calibration.ticket }}</td>

              <td v-if="calibration.date_out == '-'" class="align-middle table-warning">
                {{ calibration.date_out }}
              </td>
              <td v-else class="align-middle">{{ calibration.date_out }}</td>

              <td v-if="calibration.date_out == '-'" class="align-middle table-warning">
                {{ calibration.vehicle }}
              </td>
              <td v-else class="align-middle">{{ calibration.vehicle }}</td>

              <td v-if="calibration.date_out == '-'" class="align-middle table-warning">
                {{ calibration.product_status }}
              </td>
              <td v-else class="align-middle">
                {{ calibration.product_status }}
              </td>

              <td v-if="calibration.date_out == '-'" class="align-middle table-warning">
                {{ calibration.product_name }}
              </td>
              <td v-else class="align-middle">{{ calibration.product_name }}</td>

              <td v-if="calibration.date_out == '-'" class="align-middle table-warning">
                <span v-if="calibration.customer == '-'">{{ calibration.supplier }}</span>
                <span v-else>{{ calibration.customer }}</span>
              </td>
              <td v-else class="align-middle">
                <span v-if="calibration.customer == '-'">{{ calibration.supplier }}</span>
                <span v-else>{{ calibration.customer }}</span>
              </td>

              <td v-if="calibration.date_out == '-'" class="align-middle table-warning">{{ new Intl.NumberFormat("id-ID").format(calibration.gross) }} KG</td>
              <td v-else class="align-middle">{{ new Intl.NumberFormat("id-ID").format(calibration.gross) }} KG</td>

              <td v-if="calibration.date_out == '-'" class="align-middle table-warning">{{ new Intl.NumberFormat("id-ID").format(calibration.tare) }} KG</td>
              <td v-else class="align-middle">{{ new Intl.NumberFormat("id-ID").format(calibration.tare) }} KG</td>

              <td v-if="calibration.date_out == '-'" class="align-middle table-warning">{{ new Intl.NumberFormat("id-ID").format(calibration.nett) }} KG</td>
              <td v-else class="align-middle">{{ new Intl.NumberFormat("id-ID").format(calibration.nett) }} KG</td>
              <td v-if="calibration.date_out == '-'" class="align-middle table-warning">
                {{ new Intl.NumberFormat("id-ID").format(calibration.price) }}
                IDR
              </td>
              <td v-else class="align-middle">
                {{ new Intl.NumberFormat("id-ID").format(calibration.price) }}
                IDR
              </td>

              <td v-if="calibration.date_out == '-'" class="align-middle table-warning">
                {{ new Intl.NumberFormat("id-ID").format(calibration.total_price) }}
                IDR
              </td>
              <td v-else class="align-middle">
                {{ new Intl.NumberFormat("id-ID").format(calibration.total_price) }}
                IDR
              </td>
              <td v-if="calibration.date_out == '-'" class="align-middle table-warning">
                <button @click="setDetail(index)" data-bs-toggle="modal" data-bs-target="#detailModal" class="btn btn-sm btn-info text-white">Detail</button>
                <button v-if="level == 'admin'" @click="editData(index)" data-bs-dismiss="modal" type="button" class="btn btn-sm btn-success">Edit</button>
                <button @click="deleteData(calibration.ticket)" v-if="level == 'admin'" class="btn btn-sm btn-danger text-white">Hapus</button>
              </td>
              <td v-else class="align-middle">
                <button @click="setDetail(index)" data-bs-toggle="modal" data-bs-target="#detailModal" class="btn btn-sm btn-info text-white">Detail</button>
                <button v-if="level == 'admin'" @click="editData(index)" data-bs-dismiss="modal" type="button" class="btn btn-sm btn-success">Edit</button>
                <button @click="deleteData(calibration.ticket)" v-if="level == 'admin'" class="btn btn-sm btn-danger text-white">Hapus</button>
              </td>
            </tr>
          </tbody>
          <tfoot class="table-light table-bordered">
            <tr>
              <td class="total-col" colspan="6">Total</td>
              <td class="total-col">{{ new Intl.NumberFormat("id-ID").format(calibrationsTotal.total_gross) }} KG</td>
              <td class="total-col">{{ new Intl.NumberFormat("id-ID").format(calibrationsTotal.total_tare) }} KG</td>
              <td class="total-col">{{ new Intl.NumberFormat("id-ID").format(calibrationsTotal.total_nett) }} KG</td>
              <td class="total-col"></td>
              <td class="total-col">{{ new Intl.NumberFormat("id-ID").format(calibrationsTotal.total_price) }} IDR</td>
              <td></td>
            </tr>
          </tfoot>
        </table>
      </div>
    </div>

    <div class="modal fade" id="filterModal" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Filter Data</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form id="filter-form">
              <div class="mb-2">
                <label for="start_date" class="form-label">Dari Tanggal</label>
                <div class="input-group">
                  <input id="start_date" v-model="start_date" type="date" class="form-control shadow-none border-end-0" />
                  <div class="input-group-text bg-white"><i class="fa fa-calendar" aria-hidden="true"></i></div>
                </div>
              </div>
              <div class="mb-2">
                <label for="end_date" class="form-label">Sampai Tanggal</label>
                <div class="input-group">
                  <input id="end_date" v-model="end_date" type="date" class="form-control shadow-none border-end-0" />
                  <div class="input-group-text bg-white"><i class="fa fa-calendar" aria-hidden="true"></i></div>
                </div>
              </div>
              <div class="mb-2">
                <label for="productStatus" class="form-label">Status Barang</label>
                <select id="productStatus" v-model="productStatus" class="form-control form-control-sm mb-1">
                  <option>-</option>
                  <option>KELUAR</option>
                  <option>MASUK</option>
                </select>
              </div>
              <div class="mb-2">
                <label for="products" class="form-label">Nama Barang</label>
                <select id="products" v-model="productName" class="form-control form-control-sm mb-1">
                  <option>-</option>
                  <option v-for="(product, index) in products" :key="index">
                    {{ product }}
                  </option>
                </select>
              </div>
              <div class="mb-2">
                <label for="customers" class="form-label">Kostumer</label>
                <select id="customers" v-model="cs" class="form-control form-control-sm mb-1">
                  <option>-</option>
                  <option v-for="(customer, index) in customers" :key="index">
                    {{ customer }}
                  </option>
                </select>
              </div>
              <div class="mb-2">
                <label for="suppliers" class="form-label">Supplier</label>
                <select id="suppliers" v-model="sp" class="form-control form-control-sm mb-1">
                  <option>-</option>
                  <option v-for="(supplier, index) in suppliers" :key="index">
                    {{ supplier }}
                  </option>
                </select>
              </div>
            </form>
          </div>
          <div class="modal-footer d-flex justify-content-between">
            <button type="button" class="btn btn-secondary m-1" data-bs-dismiss="modal">Tutup</button>
            <button @click="filterData()" data-bs-dismiss="modal" type="button" class="btn btn-success m-1">Filter</button>
          </div>
        </div>
      </div>
    </div>

    <div class="modal fade" id="detailModal" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-xl">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Detail</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <div class="row">
              <div class="col-sm">
                <div class="row border-bottom border-top p-1">
                  <div class="col-sm text-left">
                    <span>TIKET</span>
                  </div>
                  <div class="col-sm text-left">
                    <span>: {{ details.ticket }}</span>
                  </div>
                </div>
                <div class="row border-bottom p-1">
                  <div class="col-sm text-left">
                    <span>TANGGAL MASUK</span>
                  </div>
                  <div class="col-sm text-left">
                    <span>: {{ details.date_in }}</span>
                  </div>
                </div>
                <div class="row border-bottom p-1">
                  <div class="col-sm text-left">
                    <span>TANGGAL KELUAR</span>
                  </div>
                  <div class="col-sm text-left">
                    <span>: {{ details.date_out }}</span>
                  </div>
                </div>
                <div class="row border-bottom p-1">
                  <div class="col-sm text-left">
                    <span>NO. KENDARAAN</span>
                  </div>
                  <div class="col-sm text-left">
                    <span class="text-uppercase">: {{ details.vehicle }}</span>
                  </div>
                </div>
                <div class="row border-bottom p-1">
                  <div class="col-sm text-left">
                    <span>STATUS BARANG</span>
                  </div>
                  <div class="col-sm text-left">
                    <span>: {{ details.product_status }}</span>
                  </div>
                </div>
                <div class="row border-bottom p-1">
                  <div class="col-sm text-left">
                    <span>NAMA BARANG</span>
                  </div>
                  <div class="col-sm text-left">
                    <span>: {{ details.product_name }}</span>
                  </div>
                </div>
                <div v-if="details.customer != '-'" class="row border-bottom p-1">
                  <div class="col-sm text-left">
                    <span>KOSTUMER</span>
                  </div>
                  <div class="col-sm text-left">
                    <span>: {{ details.customer }}</span>
                  </div>
                </div>
                <div v-if="details.supplier != '-'" class="row border-bottom p-1">
                  <div class="col-sm text-left">
                    <span>SUPPLIER</span>
                  </div>
                  <div class="col-sm text-left">
                    <span>: {{ details.supplier }}</span>
                  </div>
                </div>
                <div class="row border-bottom p-1">
                  <div class="col-sm text-left">
                    <span>NO. ORDER</span>
                  </div>
                  <div class="col-sm text-left">
                    <span class="text-uppercase">: {{ details.order_id }}</span>
                  </div>
                </div>
                <div class="row border-bottom p-1">
                  <div class="col-sm text-left">
                    <span>SUPIR</span>
                  </div>
                  <div class="col-sm text-left">
                    <span class="text-uppercase">: {{ details.driver }}</span>
                  </div>
                </div>
                <div class="row border-bottom p-1">
                  <div class="col-sm text-left">
                    <span>OPERATOR</span>
                  </div>
                  <div class="col-sm text-left">
                    <span>: {{ details.operator }}</span>
                  </div>
                </div>
                <div class="row border-bottom p-1">
                  <div class="col-sm text-left">
                    <span>KETERANGAN</span>
                  </div>
                  <div class="col-sm text-left">
                    <span class="text-uppercase">: {{ details.info }}</span>
                  </div>
                </div>
              </div>

              <div class="col-sm">
                <div class="row border-bottom border-top p-1">
                  <div class="col-sm text-left">
                    <span>BRUTO</span>
                  </div>
                  <div class="col-sm text-left">
                    <span>: {{ new Intl.NumberFormat("id-ID").format(details.gross) }}</span>
                  </div>
                </div>
                <div class="row border-bottom p-1">
                  <div class="col-sm text-left">
                    <span>TARA</span>
                  </div>
                  <div class="col-sm text-left">
                    <span>: {{ new Intl.NumberFormat("id-ID").format(details.tare) }}</span>
                  </div>
                </div>
                <div class="row border-bottom p-1">
                  <div class="col-sm text-left">
                    <span>HASIL</span>
                  </div>
                  <div class="col-sm text-left">
                    <span>: {{ new Intl.NumberFormat("id-ID").format(details.result) }}</span>
                  </div>
                </div>
                <div class="row border-bottom p-1">
                  <div class="col-sm text-left">
                    <span>POTONGAN</span>
                  </div>
                  <div class="col-sm text-left">
                    <span>: {{ new Intl.NumberFormat("id-ID").format(details.sort) }}</span>
                  </div>
                </div>
                <div class="row border-bottom p-1">
                  <div class="col-sm text-left">
                    <span>PERSEN POTONGAN</span>
                  </div>
                  <div class="col-sm text-left">
                    <span>: {{ details.percent }}%</span>
                  </div>
                </div>
                <div class="row border-bottom p-1">
                  <div class="col-sm text-left">
                    <span>NETTO</span>
                  </div>
                  <div class="col-sm text-left">
                    <span>: {{ new Intl.NumberFormat("id-ID").format(details.nett) }}</span>
                  </div>
                </div>
                <div class="row border-bottom p-1">
                  <div class="col-sm text-left">
                    <span>HARGA</span>
                  </div>
                  <div class="col-sm text-left">
                    <span>: {{ new Intl.NumberFormat("id-ID").format(details.price) }}</span>
                  </div>
                </div>
                <div class="row border-bottom p-1">
                  <div class="col-sm text-left">
                    <span>TOTAL HARGA</span>
                  </div>
                  <div class="col-sm text-left">
                    <span>: {{ new Intl.NumberFormat("id-ID").format(details.total_price) }}</span>
                  </div>
                </div>
                <div class="row border-bottom p-1">
                  <div class="col-sm text-left">
                    <span>CASH</span>
                  </div>
                  <div class="col-sm text-left">
                    <span>: {{ new Intl.NumberFormat("id-ID").format(details.cash) }}</span>
                  </div>
                </div>
                <div class="row border-bottom p-1">
                  <div class="col-sm text-left">
                    <span>TRANSFER</span>
                  </div>
                  <div class="col-sm text-left">
                    <span>: {{ new Intl.NumberFormat("id-ID").format(details.transfer) }}</span>
                  </div>
                </div>
                <div class="row border-bottom p-1">
                  <div class="col-sm text-left">
                    <span>TIMBANGAN LUAR</span>
                  </div>
                  <div class="col-sm text-left">
                    <span>: {{ new Intl.NumberFormat("id-ID").format(details.out_weight) }}</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="modal-footer d-flex justify-content-end">
            <button type="button" class="btn btn-secondary m-1" data-bs-dismiss="modal">Tutup</button>            
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { computed } from "@vue/reactivity";
import axios from "../services/axios";
import moment from "moment";
export default {
  data() {
    return {
      calibrations: [],
      calibrationData: [],
      calibrationsTotal: {
        total_gross: 0,
        total_tare: 0,
        total_nett: 0,
        total_price: 0,
      },
      products: [],
      customers: [],
      suppliers: [],
      details: [],
      level: null,
    };
  },

  methods: {
    setData() {
      this.calibrationData = computed(() => this.$store.state.calibrationData);
      this.calibrations = this.calibrationData;
      this.showing = "Menampilkan : Seluruh data.";
      this.sumData();
    },

    reSetData() {
      if (this.showing == "Menampilkan : Seluruh data.") {
        this.setData();
      } else {
        this.calibrationData = computed(() => this.$store.state.calibrationData);
        this.calibrations = this.calibrationData;
        this.filterData();
      }
    },

    sumData() {
      this.calibrationsTotal.total_gross = this.calibrations.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.gross);
      }, 0);
      this.calibrationsTotal.total_tare = this.calibrations.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.tare);
      }, 0);
      this.calibrationsTotal.total_nett = this.calibrations.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.nett);
      }, 0);
      this.calibrationsTotal.total_price = this.calibrations.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.total_price);
      }, 0);
    },

    setFilterData() {
      this.products = [...new Set(this.calibrations.map((item) => item.product_name))];
      this.customers = [...new Set(this.calibrations.map((item) => item.customer))];
      this.suppliers = [...new Set(this.calibrations.map((item) => item.supplier))];
    },

    filterData() {
      let model = {
        start: this.start_date,
        end: this.end_date,
        status: this.productStatus,
        product: this.productName,
        customer: this.cs,
        supplier: this.sp,
      };

      let sd = "";
      if (this.start_date == "" || this.start_date == null) {
        sd = "";
      } else {
        sd = "[ Dari : " + this.start_date + " ]";
      }

      let ed = "";
      if (this.end_date == "" || this.end_date == null) {
        ed = "";
      } else {
        ed = "[ Sampai : " + this.end_date + " ]";
      }

      let st = "";
      if (this.productStatus == "" || this.productStatus == null || this.productStatus == "-") {
        st = "";
      } else {
        st = "[ Status : " + this.productStatus + " ]";
      }

      let pd = "";
      if (this.productName == "" || this.productName == null || this.productName == "-") {
        pd = "";
      } else {
        pd = "[ Barang : " + this.productName + " ]";
      }

      let css = "";
      if (this.cs == "" || this.cs == null || this.cs == "-") {
        css = "";
      } else {
        css = "[ Kostumer : " + this.cs + " ]";
      }

      let spp = "";
      if (this.sp == "" || this.sp == null || this.sp == "-") {
        spp = "";
      } else {
        spp = "[ Supplier : " + this.sp + " ]";
      }

      var startDate;
      if (model.start == "" || model.start == null || model.start == undefined) {
        var itemStart = this.calibrations.at(0);
        var dateStart = itemStart.date_out;
        var arrStart = dateStart.split(" ");
        var theDateStart = arrStart[0];
        var newdateStart = theDateStart.split("-").reverse().join("-");
        var resultStart = newdateStart;
        startDate = moment.utc(resultStart).toISOString();
      } else {
        startDate = moment.utc(model.start + "T00:00:00.000Z").toISOString();
      }

      var endDate;
      if (model.end == "" || model.end == null || model.end == undefined) {
        var itemEnd = this.calibrations.at(-1);
        var dateEnd = itemEnd.date_out;
        var arrEnd = dateEnd.split(" ");
        var theDateEnd = arrEnd[0];
        var newdateEnd = theDateEnd.split("-").reverse().join("-");
        var resultEnd = newdateEnd;
        endDate = moment.utc(resultEnd).toISOString();
      } else {
        endDate = moment.utc(model.end + "T23:59:59.000Z").toISOString();
      }

      var filteredData = [];

      filteredData = this.calibrationData.filter(function (value) {
        var date = value.date_out;
        var arr = date.split(" ");
        var theDate = arr[0];
        var newdate = theDate.split("-").reverse().join("-");
        var result = newdate;
        var dates = moment.utc(result).toISOString();

        var pStatus;
        if (model.status == undefined || model.status == null || model.status == "" || model.status == "-") {
          pStatus = value.product_status != null;
        } else {
          pStatus = value.product_status == model.status;
        }

        var pName;
        if (model.product == undefined || model.product == null || model.product == "" || model.product == "-") {
          pName = value.product_name != null;
        } else {
          pName = value.product_name == model.product;
        }

        var custo;
        if (model.customer == undefined || model.customer == null || model.customer == "" || model.customer == "-") {
          custo = value.customer != null;
        } else {
          custo = value.customer == model.customer;
        }

        var sup;
        if (model.supplier == undefined || model.supplier == null || model.supplier == "" || model.supplier == "-") {
          sup = value.supplier != null;
        } else {
          sup = value.supplier == model.supplier;
        }

        return dates >= startDate && dates <= endDate && pStatus && pName && custo && sup;
      });

      this.calibrations = filteredData;

      this.showing = "Filter : " + sd + ed + st + pd + css + spp;

      this.sumData();
    },

    resetForm() {
      document.getElementById("filter-form").reset();
      this.start_date = null;
      this.end_date = null;
      this.productStatus = null;
      this.productName = null;
      this.cs = null;
      this.sp = null;
      this.setData();
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
                axios.get("calibration-data").then((result) => {
                  this.$store.dispatch("calibrationData", result.data.calibrations);
                  if (this.showing == "Menampilkan : Seluruh data.") {
                    this.reSetData();
                  } else {
                    this.filterData();
                  }
                });
                this.$swal.fire("Deleted!", result.data.messages, "success");
              } else {
                this.$swal.fire("Failed!", result.data.messages, "info");
              }
            });
          }
        });
    },

    setDetail(value) {
      this.details = this.calibrations[value];
    },

    editData(value) {
      this.details = this.calibrations[value];
      this.$store.dispatch("editData", this.details);
    },
  },

  mounted() {
    this.level = computed(() => this.$store.state.level);
    this.setData();
    this.setFilterData();
    setInterval(this.reSetData, 10000);
  },
};
</script>

<style>
#calibration-table {
  font-size: 12px;
  font-style: normal;
}

table button {
  margin: 2px;
  min-width: 60px;
}

.total-col {
  font-size: 12px;
  font-weight: bold;
}

.table-head {
  position: sticky;
  top: 0;
}

#filter-form input {
  min-height: 35px;
}

#filter-form select {
  min-height: 35px;
}
</style>
