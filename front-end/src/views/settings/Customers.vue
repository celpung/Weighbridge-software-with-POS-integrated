<template>
  <div class="container-fluid">
    <div class="container-fluid pt-3 pb-3 d-flex justify-content-end">
      <button data-bs-target="#addCustomers" data-bs-toggle="modal" class="btn btn-basic">TAMBAH KOSTUMER</button>
    </div>
    <div class="container-fluid">
      <div class="table-responsive">
        <table id="datatable" class="table table-sm table-bordered text-sm-center text-nowrap">
          <thead class="table-light">
            <tr>
              <th class="align-middle font-weight-normal" scope="col">NAMA KOSTUMER</th>
              <th class="align-middle font-weight-normal" scope="col">ACTION</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(customer, index) in customers" :key="index">
              <td class="align-middle">{{ customer.customer_name }}</td>
              <td class="align-middle">
                <button @click="editCustomer(index)" data-bs-toggle="modal" data-bs-target="#editCustomers" class="btn btn-sm btn-success mr-1" style="width: 70px">Edit</button>
                <button @click="deleteCustomer(index)" class="btn btn-sm btn-danger ml-1" style="width: 70px">Delete</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- modal edit products -->
    <div class="modal fade" id="editCustomers" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Edit Kostumer</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <form>
            <div class="modal-body">
              <div class="container">
                <span class="mb-2" style="font-size: 14px">Nama Kostumer :</span>
                <input type="text" v-model="editName" class="form-control" :placeholder="currentName" />
              </div>
            </div>
            <div class="modal-footer d-flex justify-content-between">
              <button type="reset" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
              <button type="reset" class="btn btn-info ml-2" data-bs-dismiss="modal" @click="edit()">EDIT KOSTUMER</button>
            </div>
          </form>
        </div>
      </div>
    </div>

    <!-- modal add cs -->
    <div class="modal fade" id="addCustomers" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Tambah Kostumer</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <form>
            <div class="modal-body">
              <div class="container">
                <span class="mb-2" style="font-size: 14px">Nama Kostumer :</span>
                <input type="text" v-model="addName" class="form-control" />
              </div>
            </div>
            <div class="modal-footer d-flex justify-content-between">
              <button type="reset" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
              <button type="reset" class="btn btn-info ml-2" data-bs-dismiss="modal" @click="add()">TAMBAH KOSTUMER</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "../../services/axios";
export default {
  data() {
    return {
      customers: [],
      editName: "",
      currentName: "",
      name: "",
      addName: "",
    };
  },
  methods: {
    getCustomers() {
      axios.get("get-customers").then((result) => {
        this.customers = result.data;
      });
    },

    editCustomer(value) {
      this.currentName = this.customers[value].customer_name;
    },

    edit() {
      if (this.editName == "") {
        this.name = this.currentName;
      } else {
        this.name = this.editName;
      }

      const model = {
        recent_name: this.currentName,
        new_name: this.name,
      };
      axios.post("edit-customers", model).then((result) => {
        if (result.data.status == "success") {
          this.getCustomers();
          this.$swal.fire("success", "Kostumer berhasil di edit", "success");
        } else {
          this.$swal.fire("error", "Kostumer gagal di edit", "error");
        }
      });
    },

    add() {
      const model = {
        customer_name: this.addName,
      };
      axios.post("add-customers", model).then((result) => {
        if (result.data.status == "success") {
          this.getCustomers();
          this.$swal.fire("success", "Kostumer berhasil di tambahkan", "success");
          this.addName = "";
        } else {
          this.$swal.fire("error", "Kostumer gagal di edit", "error");
          this.addName = "";
        }
      });
    },

    deleteCustomer(value) {
      this.$swal
        .fire({
          title: "Hapus kostumer ?",
          showCancelButton: true,
          confirmButtonText: "Hapus",
        })
        .then((result) => {
          if (result.isConfirmed) {
            const model = {
              customer_name: this.customers[value].customer_name,
            };
            axios.post("delete-customers", model).then((result) => {
              if (result.data.status == "success") {
                this.getCustomers();
                this.$swal.fire("success", "kostumer berhasil di hapus", "success");
              } else {
                this.$swal.fire("error", "kostumer gagal di hapus", "error");
              }
            });
          }
        });
    },
  },

  mounted() {
    this.getCustomers();
  },
};
</script>

<style></style>
