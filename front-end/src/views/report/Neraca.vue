<template>
  <div class="container-fluid">
    <div class="row border m-3 p-3">
      <div class="row d-flex justify-content-end">
        <div class="col-auto">
          <button data-bs-toggle="modal" data-bs-target="#filterModalNeraca" class="btn btn-sm btn-basic">Susun</button>
        </div>
      </div>
      <h5 class="text-center">Neraca</h5>
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
import InputNeraca from "./InputNeraca.vue";
export default {
  name: "Neraca",
  components: {
    InputNeraca,
  },
  data() {
    return {};
  },

  methods: {},
};
</script>
