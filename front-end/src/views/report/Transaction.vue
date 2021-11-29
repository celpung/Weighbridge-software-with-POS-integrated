<template>
  <div class="container-fluid">
    <div class="row d-flex justify-content-center align-items-center" style="min-height: 92vh;">
      <div class="conteiner" style="max-width: 400px;">
        <form @submit.prevent="addTransaction()">
          <div class="mb-2">
            <label for="date" class="form-label">Pilih Tanggal</label>
            <div class="input-group">
              <input id="date" v-model="date" type="date" class="form-control shadow-none border-end-0" />
              <div class="input-group-text bg-white"><i class="fa fa-calendar" aria-hidden="true"></i></div>
            </div>
          </div>

          <div class="mb-2">
            <label for="select_type" class="form-label">Pilih Tipe</label>
            <select id="select_type" v-model="type" class="form-control" v-on:change="showTag()">
              <option>-</option>
              <option value="tambah_dana">Tambah Dana</option>
              <option value="expense">Pengeluaran</option>
              <option value="income">Pemasukan</option>
            </select>
          </div>

          <div class="mb-2">
            <label for="select_account" class="form-label">Pilih Akun</label>
            <select id="select_account" v-model="account" class="form-control">
              <option>-</option>
              <option>KAS</option>
              <option>Patty</option>
              <option>Bank</option>
            </select>
          </div>

          <div class="mb-2">
            <label for="select_type" class="form-label">Tag</label>
            <select id="select_type" v-model="tag" class="form-control">
              <option value="-">-</option>
              <option value="Beban Penjualan">Beban Penjualan</option>
              <option value="Beban Operasional">Beban Operasional</option>
              <option value="Beban Administrasi">Beban Administrasi</option>
              <option value="tambah_dana">Tambah Dana</option>
            </select>
          </div>

          <div class="mb-2">
            <label for="info" class="form-label">Keterangan</label>
            <input type="text" v-model="info" class="form-control shadow-none" />
          </div>

          <div class="mb-2">
            <label for="ammount" class="form-label">Jumlah</label>
            <input type="text" v-model="ammount" class="form-control shadow-none" />
          </div>

          <div class="mb-2 d-flex justify-content-end">
            <button type="submit" class="btn btn-basic">Submit</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from '../../services/axios'

export default {
  name: "Transaction",
  methods: {
    addTransaction() {
      let model = {
        date: this.date,
        type: this.type,
        account: this.account,
        specific: this.specific,
        info: this.info,
        ammount: this.ammount,
        tag: this.tag,
      };

      axios.post('add-transaction', model).then((result) => {
        if (result.data.status == "success") {
          this.$swal.fire("success", result.data.messages, "success");
        }
      });
    }
  },
}
</script>

<style></style>
