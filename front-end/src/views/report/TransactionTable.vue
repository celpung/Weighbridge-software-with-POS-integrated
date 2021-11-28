<template>
  <div class="container-fluid">
    <div class="row border m-3 p-3">
      <div class="row d-flex justify-content-end">
        <div class="col-auto">
          <button data-bs-toggle="modal" data-bs-target="#filterTransactionTable" class="btn btn-sm btn-basic">Filter</button>
        </div>
      </div>
      <h5 class="text-center">Detail Transaksi</h5>
      <h6 class="text-center">Periode {{ sd_table }} s/d {{ ed_table }}</h6>
      <div class="table-responsive" style="max-height: 55vh">
        <table class="table text-center">
          <thead>
            <tr>
              <th scope="col">Tanggal</th>
              <th scope="col">Akun</th>
              <th scope="col">Keterangan</th>
              <th scope="col">Jumlah</th>
              <th scope="col">Tag</th>
              <th scope="col">Spesifik</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(ex, index) in reportData" :key="index">
              <td v-if="ex.type == 'expense'" class="text-danger">{{ ex.date }}</td>
              <td v-else-if="ex.type == 'income'" class="text-success">{{ ex.date }}</td>
              <td v-else>{{ ex.date }}</td>
              <td v-if="ex.type == 'expense'" class="text-danger">{{ ex.account }}</td>
              <td v-else-if="ex.type == 'income'" class="text-success">{{ ex.account }}</td>
              <td v-else>{{ ex.account }}</td>
              <td v-if="ex.type == 'expense'" class="text-danger">{{ ex.info }}</td>
              <td v-else-if="ex.type == 'income'" class="text-success">{{ ex.info }}</td>
              <td v-else>{{ ex.info }}</td>
              <td v-if="ex.type == 'expense'" class="text-danger">{{ new Intl.NumberFormat("id-ID").format(ex.ammount) }}</td>
              <td v-else-if="ex.type == 'income'" class="text-success">{{ new Intl.NumberFormat("id-ID").format(ex.ammount) }}</td>
              <td v-else>{{ new Intl.NumberFormat("id-ID").format(ex.ammount) }}</td>
              <td v-if="ex.type == 'expense'" class="text-danger">{{ ex.tag }}</td>
              <td v-else-if="ex.type == 'income'" class="text-success">{{ ex.tag }}</td>
              <td v-else>{{ ex.tag }}</td>
              <td v-if="ex.type == 'expense'" class="text-danger">{{ ex.specific }}</td>
              <td v-else-if="ex.type == 'income'" class="text-success">{{ ex.specific }}</td>
              <td v-else>{{ ex.specific }}</td>
            </tr>
          </tbody>
          <tfoot class="table-light table-bordered">
            <tr>
              <td class="total-col" colspan="3">Total</td>
              <td class="total-col">{{ new Intl.NumberFormat("id-ID").format(totalAmmount) }} IDR</td>
              <td></td>
              <td></td>
            </tr>
          </tfoot>
        </table>
      </div>
    </div>

    <div class="modal fade" id="filterTransactionTable" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Filter</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <form>
            <div class="modal-body">
              <div class="mb-2">
                <label for="start_date" class="form-label">Dari Tanggal</label>
                <div class="input-group">
                  <input id="start_date" v-model="start_date_table" type="date" class="form-control shadow-none border-end-0" />
                  <div class="input-group-text bg-white"><i class="fa fa-calendar" aria-hidden="true"></i></div>
                </div>
              </div>
              <div class="mb-2">
                <label for="end_date" class="form-label">Sampai Tanggal</label>
                <div class="input-group">
                  <input id="end_date" v-model="end_date_table" type="date" class="form-control shadow-none border-end-0" />
                  <div class="input-group-text bg-white"><i class="fa fa-calendar" aria-hidden="true"></i></div>
                </div>
              </div>
              <div class="mb-2">
                <label for="account" class="form-label">Akun</label>
                <select id="account" v-model="account_tbl" class="form-control mb-1">
                  <option>-</option>
                  <option v-for="(account, index) in account" :key="index">
                    {{ account }}
                  </option>
                </select>
              </div>
              <div class="mb-2">
                <label for="tag" class="form-label">Tag</label>
                <select id="tag" v-model="tag_tbl" class="form-control mb-1">
                  <option>-</option>
                  <option v-for="(tag, index) in tag" :key="index">
                    {{ tag }}
                  </option>
                </select>
              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
              <button type="button" @click="filterDataTable()" data-bs-dismiss="modal" class="btn btn-primary">Filter</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { computed } from "@vue/reactivity";
import moment from "moment";
export default {
  name: "TransactionTable",
  data() {
    return {
      reports: [],
      reportData: [],
      totalAmmount: 0,
      account: null,
      tag: null,
      //specific: null,
      sd_table: null,
      ed_table: null,
    };
  },
  methods: {
    setData() {
      this.reports = computed(() => this.$store.state.reportData);
      this.reportData = this.reports;
      this.countAmmount();

      let model = {
        start: this.start_date_table,
        end: this.end_date_table,
      };
      
      if (model.start == "" || model.start == null || model.start == undefined) {
        var itemStart = this.reports.at(0);
        var dateStart = itemStart.date;
        var arrStart = dateStart.split(" ");
        var theDateStart = arrStart[0];
        var newdateStart = theDateStart.split("-").reverse().join("-");
        var resultStart = newdateStart;
        //var startDate = moment.utc(resultStart + "T00:00:00.000Z").toISOString();
        this.sd_table = resultStart;
      } else {
        //startDate = moment.utc(model.start + "T00:00:00.000Z").toISOString();
        this.sd_table = this.start_date_table;
      }
      
      if (model.end == "" || model.end == null || model.end == undefined) {
        var itemEnd = this.reports.at(-1);
        var dateEnd = itemEnd.date;
        var arrEnd = dateEnd.split(" ");
        var theDateEnd = arrEnd[0];
        var newdateEnd = theDateEnd.split("-").reverse().join("-");
        var resultEnd = newdateEnd;
        //var endDate = moment.utc(resultEnd + "T23:59:59.000Z").toISOString();
        this.ed_table = resultEnd;
      } else {
        //endDate = moment.utc(model.end + "T23:59:59.000Z").toISOString();
        this.ed_tbl = this.end_date_table;
      }      
    },

    setFilterData() {
      this.account = [...new Set(this.reports.map((item) => item.account))];
      this.tag = [...new Set(this.reports.map((item) => item.tag))];
      //this.specific = [...new Set(this.reports.map((item) => item.specific))];
    },

    countAmmount() {
      this.totalAmmount = this.reportData.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);
    },

    filterDataTable() {
      let model = {
        start_table: this.start_date_table,
        end_table: this.end_date_table,
        tags: this.tag_tbl,
        accounts: this.account_tbl,
        spesifics: this.spesific_tbl,
      };

      var startDate;
      if (model.start_table == "" || model.start_table == null || model.start_table == undefined) {
        var itemStart = this.reportData.at(-1);
        var dateStart = itemStart.date;
        startDate = moment.utc(dateStart + "T00:00:00.000Z").toISOString();
        this.sd_table = dateStart;
      } else {
        startDate = moment.utc(model.start_table + "T00:00:00.000Z").toISOString();
        this.sd_table = model.start_table;
      }

      var endDate;
      if (model.end_table == "" || model.end_table == null || model.end_table == undefined) {
        var itemEnd = this.reportData.at(0);
        var dateEnd = itemEnd.date;
        endDate = moment.utc(dateEnd + "T23:59:59.000Z").toISOString();
        this.ed_table = dateEnd;
      } else {
        endDate = moment.utc(model.end_table + "T23:59:59.000Z").toISOString();
        this.ed_table = model.end_table;
      }

      var filteredData = [];

      filteredData = this.reports.filter(function (value) {
        var date = value.date;
        var dates = moment.utc(date).toISOString();

        var tblTag;
        if (model.tags == undefined || model.tags == null || model.tags == "" || model.tags == "-") {
          tblTag = value.tag != null;
        } else {
          tblTag = value.tag == model.tags;
        }

        var tblaccount;
        if (model.accounts == undefined || model.accounts == null || model.accounts == "" || model.accounts == "-") {
          tblaccount = value.account != null;
        } else {
          tblaccount = value.account == model.accounts;
        }

        return dates >= startDate && dates <= endDate && tblTag && tblaccount;
      });

      this.reportData = filteredData;

      this.countAmmount();
    },
  },

  mounted() {
    this.setData();
    this.setFilterData();
  },
};
</script>
