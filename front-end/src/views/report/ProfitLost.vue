<template>
  <div class="container-fluid">
    <div class="row border m-3 p-3">
      <div class="row d-flex justify-content-end">
        <div class="col-auto">
          <button data-bs-toggle="modal" data-bs-target="#filterModal" class="btn btn-sm btn-basic">Filter</button>
        </div>
      </div>
      <h5 class="text-center">Laporan Laba Rugi</h5>
      <h6 class="text-center">Periode {{ sd }} s/d {{ ed }}</h6>
      <ul class="list-group list-group-flush">
        <li data-bs-toggle="modal" data-bs-target="#dataPenjualan" class="list-group-item">
          <div class="d-flex justify-content-between">
            <span>Pendapatan</span>
            <span>{{ new Intl.NumberFormat("id-ID").format(penjualan) }} IDR</span>
          </div>
        </li>

        <li data-bs-toggle="modal" data-bs-target="#hppModal" class="list-group-item">
          <div class="d-flex justify-content-between">
            <span>HPP</span>
            <span>{{ new Intl.NumberFormat("id-ID").format(hpp) }} IDR</span>
          </div>
        </li>

        <li data-bs-toggle="modal" data-bs-target="#penyusutanModal" class="list-group-item">
          <div class="d-flex justify-content-between">
            <span>Penyusutan</span>
            <span>{{ new Intl.NumberFormat("id-ID").format(penyusutan) }} IDR</span>
          </div>
        </li>

        <li data-bs-toggle="modal" data-bs-target="#operasionalModal" class="list-group-item">
          <div class="d-flex justify-content-between">
            <span>Beban Operasional</span>
            <span>{{ new Intl.NumberFormat("id-ID").format(bebanOperasional) }} IDR</span>
          </div>
        </li>

        <li data-bs-toggle="modal" data-bs-target="#administrasiModal" class="list-group-item">
          <div class="d-flex justify-content-between">
            <span>Beban Administrasi</span>
            <span>{{ new Intl.NumberFormat("id-ID").format(bebanAdministrasi) }} IDR</span>
          </div>
        </li>

        <li data-bs-toggle="modal" data-bs-target="#lainModal" class="list-group-item">
          <div class="d-flex justify-content-between">
            <span>Beban Lainya</span>
            <span>{{ new Intl.NumberFormat("id-ID").format(bebanLain) }} IDR</span>
          </div>
        </li>

        <li class="list-group-item">
          <div class="d-flex justify-content-between">
            <span><b>Laba Bersih</b></span>
            <span
              ><b>{{ new Intl.NumberFormat("id-ID").format(labaBersih) }} IDR</b></span
            >
          </div>
        </li>
      </ul>
    </div>
    <div class="modal fade" id="dataPenjualan" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-xl">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Pendapatan</h5>
            <button type="button" data-bs-toggle="modal" data-bs-target="#profitModal" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
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
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(calibration, index) in penjualanData" :key="index">
                    <td class="align-middle">{{ calibration.ticket }}</td>
                    <td class="align-middle">{{ calibration.date_out }}</td>
                    <td class="align-middle">{{ calibration.vehicle }}</td>
                    <td class="align-middle">
                      {{ calibration.product_status }}
                    </td>
                    <td class="align-middle">{{ calibration.product_name }}</td>
                    <td class="align-middle">
                      <span v-if="calibration.customer == '-'">{{ calibration.supplier }}</span>
                      <span v-else>{{ calibration.customer }}</span>
                    </td>
                    <td class="align-middle">{{ new Intl.NumberFormat("id-ID").format(calibration.gross) }} KG</td>
                    <td class="align-middle">{{ new Intl.NumberFormat("id-ID").format(calibration.tare) }} KG</td>
                    <td class="align-middle">{{ new Intl.NumberFormat("id-ID").format(calibration.nett) }} KG</td>
                    <td class="align-middle">
                      {{ new Intl.NumberFormat("id-ID").format(calibration.price) }}
                      IDR
                    </td>
                    <td class="align-middle">
                      {{ new Intl.NumberFormat("id-ID").format(calibration.total_price) }}
                      IDR
                    </td>
                  </tr>
                </tbody>
                <tfoot class="table-light table-bordered">
                  <tr>
                    <td class="total-col" colspan="6">Total</td>
                    <td class="total-col">{{ new Intl.NumberFormat("id-ID").format(penjualanTotal.total_gross) }} KG</td>
                    <td class="total-col">{{ new Intl.NumberFormat("id-ID").format(penjualanTotal.total_tare) }} KG</td>
                    <td class="total-col">{{ new Intl.NumberFormat("id-ID").format(penjualanTotal.total_nett) }} KG</td>
                    <td class="total-col"></td>
                    <td class="total-col">{{ new Intl.NumberFormat("id-ID").format(penjualanTotal.total_price) }} IDR</td>
                  </tr>
                </tfoot>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="modal fade" id="hppModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-xl">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">HPP</h5>
            <button type="button" data-bs-toggle="modal" data-bs-target="#profitModal" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <div class="container-fluid">
              <ul class="list-group list-group-flush">
                <li data-bs-toggle="modal" data-bs-target="#dataPenjualan" class="list-group-item">
                  <div class="d-flex justify-content-between">
                    <span><b>Persediaan Awal</b> = </span>
                    <span
                      ><b>{{ new Intl.NumberFormat("id-ID").format(nettoPersediaanAwal) }} Kg | {{ new Intl.NumberFormat("id-ID").format(persediaanAwal) }} IDR</b></span
                    >
                  </div>
                </li>
                <li data-bs-toggle="modal" data-bs-target="#dataPenjualan" class="list-group-item">
                  <div class="d-flex justify-content-between">
                    <span><b>Weight Average Cost</b> = (Total harga pembelian + Total harga persediaan awal) / (Total netto pembelian + Total netto persediaan awal)</span>
                    <span
                      ><b>{{ new Intl.NumberFormat("id-ID").format(wac) }} IDR</b></span
                    >
                  </div>
                </li>
                <li data-bs-toggle="modal" data-bs-target="#dataPenjualan" class="list-group-item">
                  <div class="d-flex justify-content-between">
                    <span><b>HPP</b> = Total netto penjualan * Weigh Average Cost</span>
                    <span
                      ><b>{{ new Intl.NumberFormat("id-ID").format(hpp) }} IDR</b></span
                    >
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="modal fade" id="penyusutanModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-xl">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Penyusutan</h5>
            <button type="button" data-bs-toggle="modal" data-bs-target="#profitModal" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <div class="row">
              <p>Penyusutan = Total netto penyusutan*WAC ({{ new Intl.NumberFormat("id-ID").format(penyusutan) }})</p>
            </div>
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
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(calibration, index) in penyusutanData" :key="index">
                    <td class="align-middle">{{ calibration.ticket }}</td>
                    <td class="align-middle">{{ calibration.date_out }}</td>
                    <td class="align-middle">{{ calibration.vehicle }}</td>
                    <td class="align-middle">
                      {{ calibration.product_status }}
                    </td>
                    <td class="align-middle">{{ calibration.product_name }}</td>
                    <td class="align-middle">
                      <span v-if="calibration.customer == '-'">{{ calibration.supplier }}</span>
                      <span v-else>{{ calibration.customer }}</span>
                    </td>
                    <td class="align-middle">{{ new Intl.NumberFormat("id-ID").format(calibration.gross) }} KG</td>
                    <td class="align-middle">{{ new Intl.NumberFormat("id-ID").format(calibration.tare) }} KG</td>
                    <td class="align-middle">{{ new Intl.NumberFormat("id-ID").format(calibration.nett) }} KG</td>
                    <td class="align-middle">
                      {{ new Intl.NumberFormat("id-ID").format(calibration.price) }}
                      IDR
                    </td>
                    <td class="align-middle">
                      {{ new Intl.NumberFormat("id-ID").format(calibration.total_price) }}
                      IDR
                    </td>
                  </tr>
                </tbody>
                <tfoot class="table-light table-bordered">
                  <tr>
                    <td class="total-col" colspan="6">Total</td>
                    <td class="total-col">{{ new Intl.NumberFormat("id-ID").format(penyusutanTotal.total_gross) }} KG</td>
                    <td class="total-col">{{ new Intl.NumberFormat("id-ID").format(penyusutanTotal.total_tare) }} KG</td>
                    <td class="total-col">{{ new Intl.NumberFormat("id-ID").format(penyusutanTotal.total_nett) }} KG</td>
                    <td class="total-col"></td>
                    <td class="total-col">{{ new Intl.NumberFormat("id-ID").format(penyusutanTotal.total_price) }} IDR</td>
                  </tr>
                </tfoot>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="modal fade" id="operasionalModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-xl">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Beban Operasional</h5>
            <button type="button" data-bs-toggle="modal" data-bs-target="#profitModal" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <div class="table-responsive">
              <table class="table text-center">
                <thead>
                  <tr>
                    <th scope="col">Tanggal</th>
                    <th scope="col">Akun</th>
                    <th scope="col">Keterangan</th>
                    <th scope="col">Jumlah</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(ex, index) in bebanOperasionalData" :key="index">
                    <td v-if="ex.type == 'expense'" class="text-danger">{{ ex.date }}</td>
                    <td v-else class="text-success">{{ ex.date }}</td>
                    <td v-if="ex.type == 'expense'" class="text-danger">{{ ex.account }}</td>
                    <td v-else class="text-success">{{ ex.account }}</td>
                    <td v-if="ex.type == 'expense'" class="text-danger">{{ ex.info }}</td>
                    <td v-else class="text-success">{{ ex.info }}</td>
                    <td v-if="ex.type == 'expense'" class="text-danger">{{ new Intl.NumberFormat("id-ID").format(ex.ammount) }}</td>
                    <td v-else class="text-success">{{ new Intl.NumberFormat("id-ID").format(ex.ammount) }}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="modal fade" id="administrasiModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-xl">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Beban Administrasi</h5>
            <button type="button" data-bs-toggle="modal" data-bs-target="#profitModal" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <div class="table-responsive">
              <table class="table text-center">
                <thead>
                  <tr>
                    <th scope="col">Tanggal</th>
                    <th scope="col">Akun</th>
                    <th scope="col">Keterangan</th>
                    <th scope="col">Jumlah</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(ex, index) in bebanAdministrasiData" :key="index">
                    <td v-if="ex.type == 'expense'" class="text-danger">{{ ex.date }}</td>
                    <td v-else class="text-success">{{ ex.date }}</td>
                    <td v-if="ex.type == 'expense'" class="text-danger">{{ ex.account }}</td>
                    <td v-else class="text-success">{{ ex.account }}</td>
                    <td v-if="ex.type == 'expense'" class="text-danger">{{ ex.info }}</td>
                    <td v-else class="text-success">{{ ex.info }}</td>
                    <td v-if="ex.type == 'expense'" class="text-danger">{{ new Intl.NumberFormat("id-ID").format(ex.ammount) }}</td>
                    <td v-else class="text-success">{{ new Intl.NumberFormat("id-ID").format(ex.ammount) }}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="modal fade" id="lainModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-xl">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Beban lain</h5>
            <button type="button" data-bs-toggle="modal" data-bs-target="#profitModal" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <div class="table-responsive">
              <table class="table text-center">
                <thead>
                  <tr>
                    <th scope="col">Tanggal</th>
                    <th scope="col">Akun</th>
                    <th scope="col">Keterangan</th>
                    <th scope="col">Jumlah</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(ex, index) in bebanLainData" :key="index">
                    <td v-if="ex.type == 'expense'" class="text-danger">{{ ex.date }}</td>
                    <td v-else class="text-success">{{ ex.date }}</td>
                    <td v-if="ex.type == 'expense'" class="text-danger">{{ ex.account }}</td>
                    <td v-else class="text-success">{{ ex.account }}</td>
                    <td v-if="ex.type == 'expense'" class="text-danger">{{ ex.info }}</td>
                    <td v-else class="text-success">{{ ex.info }}</td>
                    <td v-if="ex.type == 'expense'" class="text-danger">{{ new Intl.NumberFormat("id-ID").format(ex.ammount) }}</td>
                    <td v-else class="text-success">{{ new Intl.NumberFormat("id-ID").format(ex.ammount) }}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
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
            </form>
          </div>
          <div class="modal-footer d-flex justify-content-between">
            <button type="button" class="btn btn-secondary m-1" data-bs-dismiss="modal">Tutup</button>
            <button @click="setData()" data-bs-dismiss="modal" type="button" class="btn btn-success m-1">Filter</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { computed } from "@vue/reactivity";
import moment from "moment";
export default {
  name: "ProfitLost",
  data() {
    return {
      calibrationsData: [],
      calibrations: [],
      startDate: null,
      endDate: null,
      penjualanData: [],
      penjualan: 0,
      penjualanNetto: 0,

      pembelianData: [],
      pembelian: 0,

      wac: 0,
      hpp: 0,

      penyusutanData: [],
      penyusutanNetto: 0,
      penyusutan: 0,

      reports: [],
      reportData: [],
      bebanOperasionalData: [],
      bebanOperasional: 0,

      bebanAdministrasiData: [],
      bebanAdministrasi: 0,

      bebanLainData: [],
      bebanLain: 0,

      labaBersih: 0,

      penjualanTotal: {
        total_gross: 0,
        total_tare: 0,
        total_nett: 0,
        total_price: 0,
      },

      penyusutanTotal: {
        total_gross: 0,
        total_tare: 0,
        total_nett: 0,
        total_price: 0,
      },

      persediaanAwal: 0,
      nettoPersediaanAwal: 0,

      calibrationsBeforeData: [],

      start_date: null,
      end_date: null,
      sd: null,
      ed: null,
    };
  },

  methods: {
    setData() {
      let model = {
        start: this.start_date,
        end: this.end_date,
      };

      var startDate;
      if (model.start == "" || model.start == null || model.start == undefined) {
        var itemStart = this.reportData.at(0);
        var dateStart = itemStart.date;
        startDate = moment.utc(dateStart + "T00:00:00.000Z").toISOString();
        this.sd = dateStart;
      } else {
        startDate = moment.utc(model.start + "T00:00:00.000Z").toISOString();
        this.sd = model.start
      }

      var endDate;
      if (model.end == "" || model.end == null || model.end == undefined) {
        var itemEnd = this.reportData.at(-1);
        var dateEnd = itemEnd.date;
        endDate = moment.utc(dateEnd + "T23:59:59.000Z").toISOString();
        this.ed = dateEnd;
      } else {
        endDate = moment.utc(model.end + "T23:59:59.000Z").toISOString();
        this.ed = model.end;
      }

      var filteredData = this.reports.filter(function (value) {
        var date = value.date;
        var dates = moment.utc(date).toISOString();

        return dates >= startDate && dates <= endDate;
      });

      this.reportData = filteredData;

      var calibrationsBefore = this.calibrationsData.filter(function (value) {
        var date = value.date_out;
        var arr = date.split(" ");
        var theDate = arr[0];
        var newdate = theDate.split("-").reverse().join("-");
        var result = newdate;
        var dates = moment.utc(result).toISOString();

        return dates < startDate;
      });

      this.calibrationsBeforeData = calibrationsBefore;

      var clb = this.calibrationsData.filter(function (value) {
        var date = value.date_out;
        var arr = date.split(" ");
        var theDate = arr[0];
        var newdate = theDate.split("-").reverse().join("-");
        var result = newdate;
        var dates = moment.utc(result).toISOString();

        return dates >= startDate && dates <= endDate;
      });

      this.calibrations = clb;

      //per awal
      if (this.calibrationsBeforeData != "") {
        var penjDt = [];
        penjDt = this.calibrationsBeforeData.filter(function (value) {
          return value.product_status == "KELUAR" && value.product_name != "SAMPAH GILINGAN" && value.total_price != "0";
        });
        var penjNt = 0;
        penjNt = penjDt.reduce(function (accumulator, item) {
          return accumulator + parseInt(item.nett);
        }, 0);

        //data pembelian
        var pembDt = [];
        pembDt = this.calibrationsBeforeData.filter(function (value) {
          return value.product_status == "MASUK" && value.total_price != "0";
        });
        var pemb = 0;
        pemb = pembDt.reduce(function (accumulator, item) {
          return accumulator + parseInt(item.total_price);
        }, 0);
        var pembNt = 0;
        pembNt = pembDt.reduce(function (accumulator, item) {
          return accumulator + parseInt(item.nett);
        }, 0);

        //wac
        var wacc = parseInt(pemb / pembNt);
        

        //hpp
        var hpps = parseInt(penjNt * wacc);

        this.persediaanAwal = parseInt(pemb - hpps);
        this.nettoPersediaanAwal = parseInt(pembNt - penjNt);
      } else {
        this.persediaanAwal = 0;
        this.nettoPersediaanAwal = 0;
      }

      //--------------------------------
      //data penjualan
      this.penjualanData = this.calibrations.filter(function (value) {
        return value.product_status == "KELUAR" && value.product_name != "SAMPAH GILINGAN" && value.total_price != "0";
      });
      this.penjualan = this.penjualanData.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.total_price);
      }, 0);
      this.penjualanNetto = this.penjualanData.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.nett);
      }, 0);

      //data pembelian
      this.pembelianData = this.calibrations.filter(function (value) {
        return value.product_status == "MASUK" && value.total_price != "0";
      });
      this.pembelian = this.pembelianData.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.total_price);
      }, 0);
      this.pembelianNetto = this.pembelianData.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.nett);
      }, 0);

      //wac
      var wacNett = this.pembelianNetto + this.nettoPersediaanAwal;
      var wacTotalPrice = this.pembelian + this.persediaanAwal;
      this.wac = parseInt(wacTotalPrice / wacNett);
      //hpp
      this.hpp = parseInt(this.penjualanNetto * this.wac);

      //penyusutan
      this.penyusutanData = this.calibrations.filter(function (value) {
        return value.product_status == "KELUAR" && value.product_name == "SAMPAH GILINGAN" || value.product_name == "SAMPAH SHREDERD";
      });
      this.penyusutanNetto = this.penyusutanData.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.nett);
      }, 0);
      this.penyusutan = parseInt(this.penyusutanNetto * this.wac);

      //beban operasional
      this.bebanOperasionalData = this.reportData.filter(function (value) {
        return value.type == "expense" && value.tag == "Beban Operasional";
      });
      this.bebanOperasional = this.bebanOperasionalData.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);

      //beban administrasi
      this.bebanAdministrasiData = this.reportData.filter(function (value) {
        return value.type == "expense" && value.tag == "Beban Administrasi";
      });
      this.bebanAdministrasi = this.bebanAdministrasiData.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);

      //beban lain
      this.bebanLainData = this.reportData.filter(function (value) {
        return value.type == "expense" && value.tag != "Beban Operasional" && value.tag != "Beban Penjualan" && value.tag != "Beban Administrasi" && value.tag != "Pembelian" && value.tag != "Penjualan";
      });
      this.bebanLain = this.bebanLainData.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);

      this.labaBersih = parseInt(this.penjualan - this.hpp - this.penyusutan - this.bebanOperasional - this.bebanAdministrasi - this.bebanLain);
      this.sumData();
    },

    sumData() {
      this.penjualanTotal.total_gross = this.penjualanData.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.gross);
      }, 0);
      this.penjualanTotal.total_tare = this.penjualanData.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.tare);
      }, 0);
      this.penjualanTotal.total_nett = this.penjualanData.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.nett);
      }, 0);
      this.penjualanTotal.total_price = this.penjualanData.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.total_price);
      }, 0);

      //penyusutan
      this.penyusutanTotal.total_gross = this.penyusutanData.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.gross);
      }, 0);
      this.penyusutanTotal.total_tare = this.penyusutanData.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.tare);
      }, 0);
      this.penyusutanTotal.total_nett = this.penyusutanData.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.nett);
      }, 0);
      this.penyusutanTotal.total_price = this.penyusutanData.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.total_price);
      }, 0);
    },
  },

  mounted() {
    this.calibrationsData = computed(() => this.$store.state.calibrationData);
    this.reports = computed(() => this.$store.state.reportData);
    this.reportData = this.reports;
    this.calibrations = this.calibrationsData;
    this.setData();
    this.sumData();
  },
};
</script>
