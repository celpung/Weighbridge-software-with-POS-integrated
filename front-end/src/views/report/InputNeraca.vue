<template>
  <div class="container-fluid">
    <form @submit.prevent="addNeraca()">
      <div class="mb-2">
        <label for="select_month_neraca" class="form-label">Bulan</label>
        <select id="select_month_neraca" v-model="monthNeraca" class="form-control" v-on:change="showTag()">
          <option>-</option>
          <option value="tambah_dana">Januari</option>
          <option value="expense">Februari</option>
          <option value="expense">Maret</option>
          <option value="expense">April</option>
          <option value="expense">Mei</option>
          <option value="expense">Juni</option>
          <option value="expense">Juli</option>
          <option value="expense">Agustus</option>
          <option value="expense">September</option>
          <option value="expense">Oktober</option>
          <option value="expense">November</option>
          <option value="expense">Desember</option>
        </select>
      </div>

      <div class="mb-2">
        <label for="year_neraca" class="form-label">Tahun</label>
        <input id="year_neraca" type="text" v-model="yearNeraca" class="form-control shadow-none" />
      </div>

      <div class="mb-2">
        <label for="select_type_neraca" class="form-label">Pilih Tipe</label>
        <select id="select_type_neraca" v-model="typeNeraca" class="form-control" v-on:change="showTag()">
          <option>-</option>
          <option value="tambah_dana">Aktiva</option>
          <option value="expense">Pasiva</option>
        </select>
      </div>

      <div class="mb-2">
        <label for="info_neraca" class="form-label">Keterangan</label>
        <input id="info_neraca" type="text" v-model="infoNeraca" class="form-control shadow-none" />
      </div>

      <div class="mb-2">
        <label for="ammount_neraca" class="form-label">Jumlah</label>
        <input id="ammount_neraca" type="text" v-model="ammountNeraca" class="form-control shadow-none" />
      </div>

      <div class="mb-2 d-flex justify-content-end">
        <button type="submit" class="btn btn-basic">Submit</button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from "../../services/axios";
export default {
  name: "InputNeraca",
  data() {
    return {};
  },

  methods: {
    addNeraca() {
      axios.post("", {}).then((result) => {
        if (result.data.status == "success") {
          this.$swal.fire("success", result.data.messages, "success");
        } else {
          this.$swal.fire("error", result.data.messages, "error");
        }
      });
    },
  },

  mounted() {
  },
};
</script>
