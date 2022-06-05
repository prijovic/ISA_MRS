<template>
  <div class="modal fade" :id="'report-'+this.id" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
      <div class="modal-content">
        <div class="modal-header p-0 m-0" style="border-bottom: none">
        </div>
        <div class="modal-body pt-0 mt-0">
          <div class="row text-center pb-0 mb-0" style="border-bottom: 1px solid lightgray;">
            <h1 class="modal-title pb-1" style="color:#e23c52; font-weight: 700;">Report</h1>
          </div>
          <div class="row d-flex justify-content-center">
            <img src="../../../../../Images/warn.png" alt="" style="height: 50%; width:50%;"/>
          </div>
          <div>
            <p class="h5 mt-3">Please write your complaint in great detail.</p>
          </div>
          <textarea rows="3" placeholder="Leave a complaint here" class="w-100 p-2"
                    style="background-color:#e23c52; border-radius: 20px; resize: none;">

          </textarea>
        </div>
        <div class="modal-footer d-flex" style="border-top: none;">
          <button type="button" class="btn btn-secondary w-30" data-bs-dismiss="modal" style="background-color:#e23c52; color: white">Cancel</button>
          <button type="button" class="btn btn-secondary w-30" data-bs-dismiss="modal" >Save</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>


export default {
  name: "ReservationReport",
  props: ["id"],
  components: {},
  computed: {

  }
}
</script>

<style scoped>
textarea::placeholder {
  color: white;
}

textarea {
  color: white;
}
</style>