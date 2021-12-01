<template>
  <div class="container-fluid">
    <div class="container-fluid d-flex justify-content-between mb-3 mt-3">
      <button @click="back()" class="btn"><i class="fas fa-arrow-left"></i> Kembali</button>
      <div>
        <div class="row">
          <div class="col">
            <button data-bs-toggle="modal" data-bs-target="#exampleModal" class="btn btn-success">Filter</button>
          </div>
          <div class="col">
            <button data-bs-toggle="modal" data-bs-target="#trxModal" class="btn btn-success">Transaksi</button>
          </div>
        </div>
      </div>
      
    </div>
    <div class="row">
      <div class="table-responsive">
        <table class="table text-center">
          <thead>
            <tr>
              <th scope="col">Tanggal</th>
              <th scope="col">Keterangan</th>
              <th scope="col">Debet</th>
              <th scope="col">Kredit</th>
              <th scope="col">Sisa Saldo</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(bank, index) in banks.data" :key="index">
              <td>{{ bank.date }}</td>
              <td>{{ bank.info }}</td>
              <td>{{ new Intl.NumberFormat("id-ID").format(bank.debt) }}</td>
              <td>{{ new Intl.NumberFormat("id-ID").format(bank.credit) }}</td>
              <td>{{ new Intl.NumberFormat("id-ID").format(bank.balance) }}</td>
            </tr>
          </tbody>
          <tfoot>
            <tr>
              <td colspan="2">Total</td>
              <td>{{ new Intl.NumberFormat("id-ID").format(banks.sum_debt) }}</td>
              <td>{{ new Intl.NumberFormat("id-ID").format(banks.sum_credit) }}</td>
              <td>{{ new Intl.NumberFormat("id-ID").format(banks.balance) }}</td>
            </tr>
          </tfoot>
        </table>
      </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <form>
            <div class="modal-body">
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
              <button type="button" @click="filterbank()" data-bs-dismiss="modal" class="btn btn-primary">Filter</button>
            </div>
          </form>
        </div>
      </div>
    </div>
    <div class="modal fade" id="trxModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Tambah Transaksi</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <transaction />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "../../services/axios";
import Transaction from "./Transaction.vue";
export default {
  components: {
    Transaction,
  },
  name: "bank",
  data() {
    return {
      banks: [],
    };
  },

  methods: {
    getbank() {
      let loader = this.$loading.show({
        loader: "dots",
        color: "#f35b3f",
        opacity: 0.7,
      });
      axios.get("get-bank").then((result) => {
        this.banks = result.data;
        loader.hide();
      });
    },

    filterbank() {
      let loader = this.$loading.show({
        loader: "dots",
        color: "#f35b3f",
        opacity: 0.7,
      });
      axios.post('filter-bank', {start_date: "", end_date: this.end_date}).then((result) => {
        this.banks = result.data;
        loader.hide();
      });
    },

    back() {
      this.$router.go(-1);
    }
  },

  mounted() {
    this.getbank();
  },
};
</script>
