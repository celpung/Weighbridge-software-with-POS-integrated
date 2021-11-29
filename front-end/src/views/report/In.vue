<template>
  <div class="container-fluid">
    <div class="row border m-3 p-3">
      <div class="row d-flex justify-content-end">
        <div class="col-auto">
          <button data-bs-toggle="modal" data-bs-target="#filterModalIn" class="btn btn-sm btn-basic">Filter</button>
        </div>
      </div>
      <h5 class="text-center">Laporan Pembelian</h5>
      <h6 class="text-center">Periode {{ sd_in }} s/d {{ ed_in }}</h6>
      <ul class="list-group list-group-flush">
        <li class="list-group-item" v-for="(buy, index) in buying" :key="index">
          <div class="d-flex justify-content-between">
            <span>{{ buy.product }} ({{ new Intl.NumberFormat("id-ID").format(buy.totalNetto) }} Kg)</span>
            <span>{{ new Intl.NumberFormat("id-ID").format(buy.totalPrice) }} IDR</span>
          </div>
        </li>
        <li class="list-group-item">
          <div class="d-flex justify-content-between">
            <span
              ><b>Total ({{ new Intl.NumberFormat("id-ID").format(sumNetto) }} Kg)</b></span
            >
            <span
              ><b>{{ new Intl.NumberFormat("id-ID").format(sumTotalPrice) }} IDR</b></span
            >
          </div>
        </li>
      </ul>
    </div>

    <div class="modal fade" id="filterModalIn" aria-hidden="true">
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
                  <input id="start_date" v-model="start_date_in" type="date" class="form-control shadow-none border-end-0" />
                  <div class="input-group-text bg-white"><i class="fa fa-calendar" aria-hidden="true"></i></div>
                </div>
              </div>
              <div class="mb-2">
                <label for="end_date" class="form-label">Sampai Tanggal</label>
                <div class="input-group">
                  <input id="end_date" v-model="end_date_in" type="date" class="form-control shadow-none border-end-0" />
                  <div class="input-group-text bg-white"><i class="fa fa-calendar" aria-hidden="true"></i></div>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer d-flex justify-content-between">
            <button type="button" class="btn btn-secondary m-1" data-bs-dismiss="modal">Tutup</button>
            <button @click="getItem()" data-bs-dismiss="modal" type="button" class="btn btn-success m-1">Filter</button>
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
  name: "In",
  data() {
    return {
      calibrationsData: [],
      calibrations: [],
      buying: [],
      sd_in: null,
      ed_in: null,
      sumNetto: 0,
      sumTotalPrice: 0,
    };
  },

  methods: {
    getItem() {
      let model = {
        start: this.start_date_in,
        end: this.end_date_in,
      };

      var startDate;
      if (model.start == "" || model.start == null || model.start == undefined) {
        let theData = this.calibrations.filter(function (value) {
          return value.date_out != "-";
        });
        var itemStart = theData.at(-1);
        var dateStart = itemStart.date_out;
        var arrStart = dateStart.split(" ");
        var theDateStart = arrStart[0];
        var newdateStart = theDateStart.split("-").reverse().join("-");
        var resultStart = newdateStart;
        startDate = moment.utc(resultStart + "T00:00:00.000Z").toISOString();
        this.sd_in = resultStart;
      } else {
        startDate = moment.utc(model.start + "T00:00:00.000Z").toISOString();
        this.sd_in = this.start_date_in;
      }

      var endDate;
      if (model.end == "" || model.end == null || model.end == undefined) {
        let theData = this.calibrations.filter(function (value) {
          return value.date_out != "-";
        });
        var itemEnd = theData.at(0);
        var dateEnd = itemEnd.date_out;
        var arrEnd = dateEnd.split(" ");
        var theDateEnd = arrEnd[0];
        var newdateEnd = theDateEnd.split("-").reverse().join("-");
        var resultEnd = newdateEnd;
        endDate = moment.utc(resultEnd + "T23:59:59.000Z").toISOString();
        this.ed_in = resultEnd;
      } else {
        endDate = moment.utc(model.end + "T23:59:59.000Z").toISOString();
        this.ed_in = this.end_date_in;
      }

      this.calibrations = this.calibrationsData.filter(function (value) {
        var date = value.date_out;
        var arr = date.split(" ");
        var theDate = arr[0];
        var newdate = theDate.split("-").reverse().join("-");
        var result = newdate;
        var dates = moment.utc(result).toISOString();

        return dates >= startDate && dates <= endDate;
      });

      var buyingData = this.calibrations.filter(function (value) {
        return value.product_status == "MASUK" && value.total_price != "0";
      });

      var sellingList = [...new Set(buyingData.map((item) => item.product_name))];

      this.buying = [];
      (this.sumNetto = 0),
        (this.sumTotalPrice = 0),
        sellingList.forEach((element) => {
          var data = buyingData.filter(function (value) {
            return value.product_name == element;
          });
          var totalPrice = data.reduce(function (accumulator, item) {
            return accumulator + parseInt(item.total_price);
          }, 0);
          var totalNetto = data.reduce(function (accumulator, item) {
            return accumulator + parseInt(item.nett);
          }, 0);

          this.sumNetto = parseInt(totalNetto + this.sumNetto);
          this.sumTotalPrice = parseInt(totalPrice + this.sumTotalPrice);

          var arrays = { product: element, totalNetto: totalNetto, totalPrice: totalPrice };

          this.buying.push(arrays);
        });
    },
  },

  mounted() {
    this.calibrationsData = computed(() => this.$store.state.calibrationData);
    this.calibrations = this.calibrationsData;
    this.getItem();
  },
};
</script>
