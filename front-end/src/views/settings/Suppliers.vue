<template>
  <div class="container-fluid">
    <div class="container-fluid pt-3 pb-3 d-flex justify-content-end">
      <button data-bs-target="#addSuppliers" data-bs-toggle="modal" class="btn btn-basic">
        TAMBAH SUPPLIER
      </button>
    </div>
    <div class="container-fluid">
      <div class="table-responsive">
        <table id="datatable" class="table table-sm table-bordered text-sm-center text-nowrap">
          <thead class="table-light">
            <tr>
              <th class="align-middle font-weight-normal" scope="col">
                NAMA SUPPLIER
              </th>
              <th class="align-middle font-weight-normal" scope="col">
                ACTION
              </th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(supplier, index) in suppliers" :key="index">
              <td class="align-middle">{{ supplier.supplier_name }}</td>
              <td class="align-middle">
                <button @click="editSupplier(index)" data-bs-toggle="modal" data-bs-target="#editSuppliers" class="btn btn-sm btn-success mr-1" style="width:70px;">
                  Edit
                </button>
                <button @click="deleteSupplier(index)" class="btn btn-sm btn-danger ml-1" style="width:70px;">
                  Delete
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- modal edit sp -->
    <div class="modal fade" id="editSuppliers" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Edit Supplier</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <form>
            <div class="modal-body">
              <div class="container">
                <span class="mb-2" style="font-size: 14px">Nama Supplier :</span>
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

    <!-- modal add sp -->
    <div class="modal fade" id="addSuppliers" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Edit Supplier</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <form>
            <div class="modal-body">
              <div class="container">
                <span class="mb-2" style="font-size: 14px">Nama Supplier :</span>
                <input type="text" v-model="addName" class="form-control" />
              </div>
            </div>
            <div class="modal-footer d-flex justify-content-between">
              <button type="reset" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
              <button type="reset" class="btn btn-info ml-2" data-bs-dismiss="modal" @click="add()">TAMBAH SUPPLIER</button>
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
      suppliers: [],
      editName: "",
      currentName: "",
      name: "",
      addName: "",
    };
  },
  methods: {
    getSuppliers() {
      axios.get("get-suppliers").then((result) => {
        this.suppliers = result.data;
      });
    },

    editSupplier(value) {
      this.currentName = this.suppliers[value].supplier_name;
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
      axios.post("edit-suppliers", model).then((result) => {
        if (result.data.status == "success") {
          this.getSuppliers();
          this.$swal.fire("success", "supplier berhasil di edit", "success");
        } else {
          this.$swal.fire("error", "supplier gagal di edit", "error");
        }
      });
    },

    add() {
      const model = {
        supplier_name: this.addName,
      };
      axios.post("add-suppliers", model).then((result) => {
        if (result.data.status == "success") {
          this.getSuppliers();
          this.$swal.fire("success", "supplier berhasil di tambahkan", "success");
          this.addName = "";
        } else {
          this.$swal.fire("error", "supplier gagal di edit", "error");
          this.addName = "";
        }
      });
    },

    deleteSupplier(value) {
      this.$swal
        .fire({
          title: "Hapus supplier ?",
          showCancelButton: true,
          confirmButtonText: "Hapus",
        })
        .then((result) => {
          if (result.isConfirmed) {
            const model = {
              supplier_name: this.suppliers[value].supplier_name,
            };
            axios.post("delete-suppliers", model).then((result) => {
              if (result.data.status == "success") {
                this.getSuppliers();
                this.$swal.fire("success", "supplier berhasil di hapus", "success");
              } else {
                this.$swal.fire("error", "supplier gagal di hapus", "error");
              }
            });
          }
        });
    },
  },

  mounted() {
    this.getSuppliers();
  },
};
</script>

<style></style>
