<template>
  <div class="container-fluid">
    <div class="row border m-3 p-3">
      <div class="row d-flex justify-content-end">
        <div class="col-auto">
          <button data-bs-toggle="modal" data-bs-target="#filterModalNeraca" class="btn btn-sm btn-basic">Susun</button>
        </div>
      </div>
      <h5 class="text-center">Neraca</h5>
      <div class="row">
        <div class="table-responsive">
          <table class="table text-center">
            <thead>
              <tr>
                <th scope="col">Keterangan</th>
                <th scope="col">Debet</th>
                <th scope="col">Kredit</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <th colspan="3"><b>No Data</b></th>
              </tr>              
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <div class="modal fade" id="filterModalNeraca" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Filter Data</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <InputNeraca />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { computed } from "@vue/reactivity";
import axios from "axios";
import InputNeraca from "./InputNeraca.vue";
export default {
  name: "Neraca",
  components: {
    InputNeraca,
  },
  data() {
    return {
      reports: [],
      reportData: [],
      kas: 0,
      bank: 0,
      patty: 0,
      pembelian: 0,
      neracaData: [],
    };
  },

  methods: {
    setData() {
      this.reports = computed(() => this.$store.state.reportData);
      this.reportData = this.reports;

      //kas
      let kasExpenseData = this.reports.filter(function (value) {
        return value.account == "KAS" && value.type == "expense";
      });

      let kasExpense = kasExpenseData.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);

      let kasIncomeData = this.reports.filter(function (value) {
        return value.account == "KAS" && value.type == "income";
      });

      let kasIncome = kasIncomeData.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);

      let kasCapitalData = this.reports.filter(function (value) {
        return value.account == "KAS" && value.type == "tambah_dana";
      });

      let kasCapital = kasCapitalData.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);

      this.kas = parseInt(kasCapital + kasIncome) - parseInt(kasExpense);

      //bank
      let bankExpenseData = this.reports.filter(function (value) {
        return value.account == "Bank" && value.type == "expense";
      });

      let bankExpense = bankExpenseData.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);

      let bankIncomeData = this.reports.filter(function (value) {
        return value.account == "Bank" && value.type == "income";
      });

      let bankIncome = bankIncomeData.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);

      let bankCapitalData = this.reports.filter(function (value) {
        return value.account == "Bank" && value.type == "tambah_dana";
      });

      let bankCapital = bankCapitalData.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);

      this.bank = parseInt(bankCapital + bankIncome) - parseInt(bankExpense);

      //patty
      let pattyExpenseData = this.reports.filter(function (value) {
        return value.account == "Patty" && value.type == "expense";
      });

      let pattyExpense = pattyExpenseData.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);

      let pattyIncomeData = this.reports.filter(function (value) {
        return value.account == "Patty" && value.type == "income";
      });

      let pattyIncome = pattyIncomeData.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);

      let pattyCapitalData = this.reports.filter(function (value) {
        return value.account == "Patty" && value.type == "tambah_dana";
      });

      let pattyCapital = pattyCapitalData.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);

      this.patty = parseInt(pattyCapital + pattyIncome) - parseInt(pattyExpense);

      //pembelian
      let filteredDataPembelian = this.reports.filter(function (value) {
        return value.tag == "Pembelian";
      });

      this.pembelian = filteredDataPembelian.reduce(function (accumulator, item) {
        return accumulator + parseInt(item.ammount);
      }, 0);
    },

    getData() {
      axios.get("neraca").then((result) => {
        this.neracaData = result.data;
      });
    },
  },

  mounted() {
    this.setData();
  },
};
</script>
