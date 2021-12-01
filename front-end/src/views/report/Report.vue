<template>
  <div class="container-fluid">
    <div class="row">
      <div class="col">
        <div @click="patty()" style="cursor: pointer" class="card">
          <div class="card-body">
            <p>Saldo Patty Cash</p>
            <h5 class="text-success"><i class="fas fa-chart-line"></i> {{ new Intl.NumberFormat("id-ID").format(pattyAmmount) }}</h5>
          </div>
        </div>
      </div>
      <div class="col">
        <div @click="kas()" style="cursor: pointer" class="card">
          <div class="card-body">
            <p>Saldo KAS</p>
            <h5 class="text-danger"><i class="fas fa-chart-area"></i> {{ new Intl.NumberFormat("id-ID").format(kasAmmount) }}</h5>
          </div>
        </div>
      </div>
      <div class="col">
        <div @click="bank()" style="cursor: pointer" class="card">
          <div data-bs-toggle="modal" data-bs-target="#profitModal" class="card-body">
            <p>Saldo Bank</p>
            <h5 class="text-primary"><i class="fas fa-money-bill"></i> {{ new Intl.NumberFormat("id-ID").format(bankAmmount) }}</h5>
          </div>
        </div>
      </div>
    </div>
    <TransactionTable />
    <Out />
    <In />
    <ProfitLost />
    <Neraca />
  </div>
</template>

<script>
import { computed } from "@vue/reactivity";
import ProfitLost from "./ProfitLost.vue";
import Out from "./Out.vue";
import In from "./In.vue";
import TransactionTable from "./TransactionTable.vue";
import Neraca from "./Neraca.vue";
export default {
  components: {
    ProfitLost,
    Out,
    In,
    TransactionTable,
    Neraca,
  },

  data() {
    return {
      reports: [],
      reportData: [],
      pattyAmmount: 0,
      kasAmmount: 0,
      bankAmmount: 0,
    };
  },

  methods: {
    setData() {      
      this.reports = computed(() => this.$store.state.reportData);
      this.calibrations = computed(() => this.$store.state.calibrationData);
      this.reportData = this.reports;
    },

    setAccountValue() {
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

      this.pattyAmmount = parseInt(totalPattyIn) - parseInt(totalPattyOut);

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

      this.kasAmmount = parseInt(totalKasIn) - parseInt(totalKasOut);

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

      this.bankAmmount = parseInt(totalBankIn) - parseInt(totalBankOut);
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
    this.setAccountValue();
  },
};
</script>
