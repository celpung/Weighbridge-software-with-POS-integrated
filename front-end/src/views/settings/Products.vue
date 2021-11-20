<template>
  <div class="container-fluid">
    <div class="container-fluid pt-3 pb-3 d-flex justify-content-end">
      <button data-bs-target="#addProducts" data-bs-toggle="modal" class="btn btn-basic">TAMBAH BARANG</button>
    </div>
    <div class="container-fluid">
      <div class="table-responsive">
        <table id="datatable" class="table table-sm table-bordered text-sm-center text-nowrap">
          <thead class="table-light">
            <tr>
              <th class="align-middle font-weight-normal" scope="col">NAMA BARANG</th>
              <th class="align-middle font-weight-normal" scope="col">HARGA BELI</th>
              <th class="align-middle font-weight-normal" scope="col">HARGA JUAL</th>
              <th class="align-middle font-weight-normal" scope="col">ACTION</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(product, index) in products" :key="index">
              <td class="align-middle">{{ product.product_name }}</td>
              <td class="align-middle">
                {{ new Intl.NumberFormat("id-ID").format(product.buy_price) }}
              </td>
              <td class="align-middle">
                {{ new Intl.NumberFormat("id-ID").format(product.sell_price) }}
              </td>
              <td class="align-middle">
                <button @click="editProduct(index)" data-bs-toggle="modal" data-bs-target="#editProducts" class="btn btn-sm btn-success mr-1" style="width: 70px">Edit</button>
                <button @click="deleteProduct(index)" class="btn btn-sm btn-danger ml-1" style="width: 70px">Delete</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- modal edit products -->
    <div class="modal fade" id="editProducts" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Edit Barang</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <form id="editProductsForm">
            <div class="modal-body">
              <div class="container">
                <span class="mb-2" style="font-size: 14px">Nama Barang :</span>
                <input type="text" v-model="editName" class="form-control" :placeholder="currentName" />
                <span class="mb-2" style="font-size: 14px">Harga Beli :</span>
                <input type="text" v-model="editBuy" class="form-control" :placeholder="currentBuy" />
                <span class="mb-2" style="font-size: 14px">Harga Jual :</span>
                <input type="text" v-model="editSell" class="form-control" :placeholder="currentSell" />
              </div>
            </div>
            <div class="modal-footer d-flex justify-content-between">
              <button type="reset" form="editProductsForm" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
              <button type="reset" form="editProductsForm" class="btn btn-info ml-2" data-bs-dismiss="modal" @click="edit()">EDIT BARANG</button>
            </div>
          </form>
        </div>
      </div>
    </div>

    <!-- modal add products -->
    <div class="modal fade" id="addProducts" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Tambah Barang</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <form id="addProducts">
            <div class="modal-body">
              <span class="mb-2" style="font-size: 14px">Nama Barang :</span>
              <input type="text" v-model="addName" class="form-control" />
              <span class="mb-2" style="font-size: 14px">Harga Beli :</span>
              <input type="text" v-model="addBuy" class="form-control" />
              <span class="mb-2" style="font-size: 14px">Harga Jual :</span>
              <input type="text" v-model="addSell" class="form-control" />
            </div>
            <div class="modal-footer d-flex justify-content-between">
              <button type="reset" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
              <button type="reset" class="btn btn-info ml-2" data-bs-dismiss="modal" @click="add()">TAMBAH BARANG</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
//import $ from "jquery";
//import "datatables.net-bs4/css/dataTables.bootstrap4.min.css";
//import "datatables.net-bs4/js/dataTables.bootstrap4";
import axios from "../../services/axios";
export default {
  data() {
    return {
      products: [],
      editName: "",
      editBuy: "",
      editSell: "",
      currentName: "",
      currentBuy: "",
      currentSell: "",

      name: "",
      buy: "",
      sell: "",

      addName: "",
      addBuy: "",
      addSell: "",
    };
  },
  methods: {
    getProducts() {
      axios.get("get-products").then((result) => {
        this.products = result.data;
      });
    },

    editProduct(value) {
      this.currentName = this.products[value].product_name;
      this.currentBuy = this.products[value].buy_price;
      this.currentSell = this.products[value].sell_price;
    },

    edit() {
      if (this.editName == "") {
        this.name = this.currentName;
      } else {
        this.name = this.editName;
      }

      if (this.editBuy == "") {
        this.buy = this.currentBuy;
      } else {
        this.buy = this.editBuy;
      }

      if (this.editSell == "") {
        this.sell = this.currentSell;
      } else {
        this.sell = this.editSell;
      }

      const model = {
        recent_name: this.currentName,
        new_name: this.name,
        buy_price: this.buy,
        sell_price: this.sell,
      };
      axios.post("edit-products", model).then((result) => {
        if (result.data.status == "success") {
          this.getProducts();
          this.$swal.fire("success", "Barang berhasil di edit", "success");
        } else {
          this.$swal.fire("error", "Barang gagal di edit", "error");
        }
      });
    },

    add() {
      const model = {
        product_name: this.addName,
        buy_price: this.addBuy,
        sell_price: this.addSell,
      };
      axios.post("add-products", model).then((result) => {
        if (result.data.status == "success") {
          this.getProducts();
          (this.addName = ""), (this.addBuy = ""), (this.addSell = ""), this.$swal.fire("success", "Barang berhasil di tambahkan", "success");
        } else {
          this.$swal.fire("error", "Barang gagal di edit", "error");
        }
      });
    },

    deleteProduct(value) {
      this.$swal
        .fire({
          title: "Yakin akan menghapus barang ?",
          showCancelButton: true,
          confirmButtonText: "Hapus",
          denyButtonText: `Batal`,
        })
        .then((result) => {
          if (result.isConfirmed) {
            const model = { product_name: this.products[value].product_name };
            axios.post("delete-products", model).then((result) => {
              if (result.data.status == "success") {
                this.getProducts();
                this.$swal.fire("success", "Barang berhasil di hapus", "success");
              } else {
                this.$swal.fire("error", "Barang gagal di hapus", "error");
              }
            });
          }
        });
    },
  },

  mounted() {
    this.getProducts();
    /*
    setTimeout(function() {
      $("#datatable").DataTable({
        ordering: false,
      });
    }, 3000);
    */
  },
};
</script>

<style></style>
