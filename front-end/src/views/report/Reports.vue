<template>
  <div class="container-fluid">
    <div class="container-fluid d-flex justify-content-end">
      <button data-bs-toggle="modal" data-bs-target="#exampleModal" class="btn btn-basic mb-3">Filter</button>
    </div>
    <div class="row">
      <div class="col">
        <div style="cursor: pointer" class="card">
          <div data-bs-toggle="modal" data-bs-target="#capitalModal" class="card-body">
            <p>Modal</p>
            <h5><i class="fas fa-chart-line"></i> {{ new Intl.NumberFormat("id-ID").format(totalCapital) }}</h5>
          </div>
        </div>
      </div>
      <div class="col">
        <div @click="income()" style="cursor: pointer" class="card">
          <div class="card-body">
            <p>Pemasukan</p>
            <h5 class="text-success"><i class="fas fa-chart-line"></i> {{ new Intl.NumberFormat("id-ID").format(totalIncome) }}</h5>
          </div>
        </div>
      </div>
      <div class="col">
        <div @click="expense()" style="cursor: pointer" class="card">
          <div class="card-body">
            <p>Pengeluaran</p>
            <h5 class="text-danger"><i class="fas fa-chart-area"></i> {{ new Intl.NumberFormat("id-ID").format(totalExpense) }}</h5>
          </div>
        </div>
      </div>
      <div class="col">
        <div style="cursor: pointer" class="card">
          <div data-bs-toggle="modal" data-bs-target="#profitModal" class="card-body">
            <p>Laba/Rugi</p>
            <h5 class="text-primary"><i class="fas fa-money-bill"></i> {{ new Intl.NumberFormat("id-ID").format(totalProfit) }}</h5>
          </div>
        </div>
      </div>
    </div>

    <div class="row mt-3">
      <div class="col" style="min-width: 70%">
        <h6><u>Tabel Transaksi</u></h6>
        <div class="table-responsive" style="max-height: 55vh">
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
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <div class="col">
        <div @click="patty()" style="cursor: pointer" class="card mb-3">
          <div class="card-body">
            <span>Saldo Patty Cash</span>
            <h6 class="text-primary">{{ new Intl.NumberFormat("id-ID").format(pattyAmmount) }}</h6>
          </div>
        </div>
        <div @click="kas()" style="cursor: pointer" class="card mb-3">
          <div class="card-body">
            <span>Saldo KAS</span>
            <h6 class="text-primary">{{ new Intl.NumberFormat("id-ID").format(kasAmmount) }}</h6>
          </div>
        </div>
        <div @click="bank()" style="cursor: pointer" class="card">
          <div class="card-body">
            <span>Saldo Bank</span>
            <h6 class="text-primary">{{ new Intl.NumberFormat("id-ID").format(bankAmmount) }}</h6>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
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
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
              <button type="button" @click="filterData()" data-bs-dismiss="modal" class="btn btn-primary">Filter</button>
            </div>
          </form>
        </div>
      </div>
    </div>

    <div class="modal fade" id="profitModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Laporan Laba Rugi</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <ul class="list-group list-group-flush">
              <li data-bs-toggle="modal" data-bs-target="#PembelianPenjualanModal" class="list-group-item">
                <div class="d-flex justify-content-between">
                  <span>Pendapatan</span>
                  <span>{{ new Intl.NumberFormat("id-ID").format(pendapatan) }} IDR</span>
                </div>
              </li>

              <li data-bs-toggle="modal" data-bs-target="#penjualanModal" class="list-group-item">
                <div class="d-flex justify-content-between">
                  <span>Beban Penjualan</span>
                  <span>{{ new Intl.NumberFormat("id-ID").format(totalSellingCost) }} IDR</span>
                </div>
              </li>

              <li data-bs-toggle="modal" data-bs-target="#operasionalModal" class="list-group-item">
                <div class="d-flex justify-content-between">
                  <span>Beban Operasional</span>
                  <span>{{ new Intl.NumberFormat("id-ID").format(totalOperationalCost) }} IDR</span>
                </div>
              </li>

              <li data-bs-toggle="modal" data-bs-target="#lainModal" class="list-group-item">
                <div class="d-flex justify-content-between">
                  <span>Beban Lainya</span>
                  <span>{{ new Intl.NumberFormat("id-ID").format(totalOtherCost) }} IDR</span>
                </div>
              </li>

              <li class="list-group-item">
                <div class="d-flex justify-content-between">
                  <span><b>Laba Bersih</b></span>
                  <span
                    ><b>{{ new Intl.NumberFormat("id-ID").format(clearProfit) }} IDR</b></span
                  >
                </div>
              </li>
            </ul>
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
                  <tr v-for="(ex, index) in operationalCost" :key="index">
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

    <div class="modal fade" id="penjualanModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-xl">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Beban penjualan</h5>
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
                  <tr v-for="(ex, index) in sellingCost" :key="index">
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
                  <tr v-for="(ex, index) in otherCost" :key="index">
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

    <div class="modal fade" id="capitalModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-xl">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Modal</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
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
                  <tr v-for="(ex, index) in capital" :key="index">
                    <td>{{ ex.date }}</td>
                    <td>{{ ex.account }}</td>
                    <td>{{ ex.info }}</td>
                    <td>{{ new Intl.NumberFormat("id-ID").format(ex.ammount) }}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="modal fade" id="PembelianPenjualanModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-xl">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Penjualan/Pembelian</h5>
            <button type="button" data-bs-toggle="modal" data-bs-target="#profitModal" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <h6 class="text-center"><u>Tabel Penjualan</u></h6>
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
                  <tr v-for="(ex, index) in filterSelling" :key="index">
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
                <tfoot>
                  <tr>
                    <td colspan="3"><b>Total</b></td>
                    <td>
                      <b>{{ new Intl.NumberFormat("id-ID").format(totalSelling) }}</b>
                    </td>
                  </tr>
                </tfoot>
              </table>
            </div>

            <h6 class="text-center mt-5"><u>Tabel Pembelian</u></h6>
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
                  <tr v-for="(ex, index) in filterBuying" :key="index">
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
                <tfoot>
                  <tr>
                    <td colspan="3"><b>Total</b></td>
                    <td>
                      <b>{{ new Intl.NumberFormat("id-ID").format(totalBuying) }}</b>
                    </td>
                  </tr>
                </tfoot>
              </table>
            </div>
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
  data() {
    return {
      reports: [],
      reportData: [],          
      totalExpense: 0,
      totalIncome: 0,
      totalProfit: 0,
      capital: [],
      totalCapital: 0,
      filterSelling: [],
      totalSelling: 0,
      filterBuying: [],
      totalBuying: 0,
      totalGrossProfit: 0,
      sellingCost: [],
      totalSellingCost: 0,
      operationalCost: [],
      totalOperationalCost: 0,
      otherCost: [],
      totalOtherCost: 0,
      clearProfit: 0,
      pattyAmmount: 0,
      kasAmmount: 0,
      bankAmmount: 0,
      calibrations: [],

      pendapatanData: [],
      pendapatan: 0,
    };
  },
  methods: {
    setData() {
      this.reports = computed(() => this.$store.state.reportData);
      this.calibrations = computed(() => this.$store.state.calibrationData);
      this.reportData = this.reports;
      this.counting();
    },

    counting() {
      this.pendapatanData = this.calibrations.filter(function (value) {
        return value.product_status == "KELUAR";
      });
      this.pendapatan = this.pendapatanData.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.total_price);
      }, 0);

      
      //----------------------------
      this.capital = this.reportData.filter(function (value) {
        return value.type == "tambah_dana";
      });
      this.totalCapital = this.capital.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);
      var filteredExpense = [];
      filteredExpense = this.reportData.filter(function (value) {
        return value.type == "expense";
      });
      this.totalExpense = filteredExpense.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);
      var filteredIncome = [];
      filteredIncome = this.reportData.filter(function (value) {
        return value.type == "income";
      });
      this.totalIncome = filteredIncome.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);
      this.totalProfit = parseInt(this.totalIncome) - parseInt(this.totalExpense);

      this.filterSelling = this.reportData.filter(function (value) {
        return value.type == "income" && value.tag == "Penjualan";
      });
      this.totalSelling = this.filterSelling.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);

      this.filterBuying = this.reportData.filter(function (value) {
        return value.type == "expense" && value.tag == "Pembelian";
      });
      this.totalBuying = this.filterBuying.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);

      this.totalGrossProfit = parseInt(this.totalSelling) - parseInt(this.totalBuying);

      this.sellingCost = this.reportData.filter(function (value) {
        return value.type == "expense" && value.tag == "Beban Penjualan";
      });
      this.totalSellingCost = this.sellingCost.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);

      this.operationalCost = this.reportData.filter(function (value) {
        return value.type == "expense" && value.tag == "Beban Operasional";
      });
      this.totalOperationalCost = this.operationalCost.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);

      this.otherCost = this.reportData.filter(function (value) {
        return value.type == "expense" && value.tag != "Beban Operasional" && value.tag != "Beban Penjualan" && value.tag != "Pembelian" && value.tag != "Penjualan";
      });
      this.totalOtherCost = this.otherCost.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);

      this.clearProfit = parseInt(this.totalGrossProfit) - parseInt(this.totalSellingCost) - parseInt(this.totalOperationalCost) - parseInt(this.totalOtherCost);

      //account
      var pattyIn = [];
      pattyIn = this.reports.filter(function (value) {
        return value.account == "Patty" && value.type != "expense";
      });
      var totalPattyIn = pattyIn.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);

      var pattyOut = [];
      pattyOut = this.reports.filter(function (value) {
        return value.account == "Patty" && value.type == "expense";
      });
      var totalPattyOut = pattyOut.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);

      this.pattyAmmount = parseInt(totalPattyIn)-parseInt(totalPattyOut);

      var kasIn = [];
      kasIn = this.reports.filter(function (value) {
        return value.account == "KAS" && value.type != "expense";
      });
      var totalKasIn = kasIn.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);

      var kasOut = [];
      kasOut = this.reports.filter(function (value) {
        return value.account == "KAS" && value.type == "expense";
      });
      var totalKasOut = kasOut.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);

      this.kasAmmount = parseInt(totalKasIn)-parseInt(totalKasOut);

      var bankIn = [];
      bankIn = this.reports.filter(function (value) {
        return value.account == "Bank" && value.type != "expense";
      });
      var totalBankIn = bankIn.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);

      var bankOut = [];
      bankOut = this.reportData.filter(function (value) {
        return value.account == "Bank" && value.type == "expense";
      });
      var totalBankOut = bankOut.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);

      this.bankAmmount = parseInt(totalBankIn)-parseInt(totalBankOut);
    },

    filterData() {
      let model = {
        start: this.start_date,
        end: this.end_date,
      };

      var startDate;
      if (model.start == "" || model.start == null || model.start == undefined) {
        var itemStart = this.reportData.at(0);
        var dateStart = itemStart.date;
        startDate = moment.utc(dateStart + "T00:00:00.000Z").toISOString();
      } else {
        startDate = moment.utc(model.start + "T00:00:00.000Z").toISOString();
      }

      var endDate;
      if (model.end == "" || model.end == null || model.end == undefined) {
        var itemEnd = this.reportData.at(-1);
        var dateEnd = itemEnd.date;
        endDate = moment.utc(dateEnd + "T23:59:59.000Z").toISOString();
      } else {
        endDate = moment.utc(model.end + "T23:59:59.000Z").toISOString();
      }

      var filteredData = [];

      filteredData = this.reports.filter(function (value) {
        var date = value.date;
        var dates = moment.utc(date).toISOString();

        return dates >= startDate && dates <= endDate;
      });

      this.reportData = filteredData;

      this.counting();
    },

    income() {
      this.$router.push("/income");
    },

    expense() {
      this.$router.push("/expense");
    },

    patty() {
      this.$router.push("/patty");
    },

    kas() {
      this.$router.push("/kas");
    },

    bank() {
      this.$router.push("/bank");
    },
  },

  mounted() {
    this.setData();
  },
};
</script>
