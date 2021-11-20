<template>
  <div class="container-fluid">
    <div class="row">
      <div class="container-fluid d-flex justify-content-between align-items-center mb-3 mt-3">
        <button @click="back()" class="btn"><i class="fas fa-arrow-left"></i> Kembali</button>
        <h5 class="m-0 p-0"><u>Tabel Pemasukan</u></h5>
        <div>
          <button @click="resetFilter()" class="btn btn-danger m-1">Reset</button>
          <button data-bs-toggle="modal" data-bs-target="#incomeModal" class="btn btn-success m-1">Filter</button>
        </div>
      </div>
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
            <tr v-for="(ex, index) in income" :key="index">
              <td>{{ ex.date }}</td>
              <td>{{ ex.account }}</td>
              <td>{{ ex.info }}</td>
              <td>{{ new Intl.NumberFormat("id-ID").format(ex.ammount) }}</td>
            </tr>
          </tbody>
          <tfoot>
            <tr>
              <td colspan="3">Total</td>
              <td>{{ new Intl.NumberFormat("id-ID").format(totalIncome) }}</td>
            </tr>
          </tfoot>
        </table>
      </div>
    </div>

    <!-- income Modal -->
    <div class="modal fade" id="incomeModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Filter Pengeluaran</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <form>
            <div class="modal-body">
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
                <label for="info" class="form-label">Akun</label>
                <div class="input-group">
                  <select class="form-control" v-model="account">
                    <option>-</option>
                    <option v-for="(inf, index) in accountList" :key="index">{{ inf }}</option>
                  </select>
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
              <button type="button" @click="filterincome()" data-bs-dismiss="modal" class="btn btn-primary">Filter</button>
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
  data() {
    return {
      income: [],
      totalIncome: 0,
      reports: [],
      accountList: [],
    };
  },

  methods: {
    back() {
      this.$router.go(-1);
    },

    getincome() {
      this.reports = computed(() => this.$store.state.reportData);
      this.income = this.reports.filter(function (value) {
        return value.type == "income";
      });
      this.counting();
    },

    counting() {
      this.totalIncome = this.income.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);
    },

    setFilter() {
      this.accountList = [...new Set(this.income.map((item) => item.account))];
    },

    filterincome() {
      let model = {
        start: this.start_date,
        end: this.end_date,
        account: this.account,
      };

      var startDate;
      if (model.start == "" || model.start == null || model.start == undefined) {
        var itemStart = this.income.at(0);
        var dateStart = itemStart.date;
        startDate = moment.utc(dateStart + "T00:00:00.000Z").toISOString();
      } else {
        startDate = moment.utc(model.start + "T00:00:00.000Z").toISOString();
      }

      var endDate;
      if (model.end == "" || model.end == null || model.end == undefined) {
        var itemEnd = this.income.at(-1);
        var dateEnd = itemEnd.date;
        endDate = moment.utc(dateEnd + "T23:59:59.000Z").toISOString();
      } else {
        endDate = moment.utc(model.end + "T23:59:59.000Z").toISOString();
      }

      this.income = this.reports.filter(function (value) {
        var acc;
        if (model.account == undefined || model.account == null || model.account == "" || model.account == "-") {
          acc = value.account != null;
        } else {
          acc = value.account == model.account;
        }
        var date = value.date;
        var dates = moment.utc(date).toISOString();
        return value.type == "income" && dates >= startDate && dates <= endDate && acc;
      });
      this.counting();
    },

    resetFilter() {
      this.start_date = null;
      this.end_date = null;
      this.account = "";
      this.getincome();
    },
  },

  mounted() {
    this.getincome();
    this.setFilter();
  },
};
</script>
