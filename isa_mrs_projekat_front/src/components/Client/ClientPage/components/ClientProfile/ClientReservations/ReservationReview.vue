<template>
  <div class="modal fade" :id="'review-'+this.id" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
      <div class="modal-content">
        <div class="modal-header p-0 m-0" style="border-bottom: none">
        </div>
        <div class="modal-body pt-0 mt-0">
          <div class="row text-center pb-0 mb-0" style="border-bottom: 1px solid lightgray;">
            <h1 class="modal-title pb-1" style="color:#ffd055; font-weight: 700;">Reviews</h1>
          </div>
          <div>
            <p class="h5 mt-3">What did you think about the owner/instructor?</p>
          </div>
          <div class="row my-0 py-0 my-3" >
            <div class="col-12 d-flex justify-content-center align-items-center">
              <star-rating text-class="h1 my-0 font-weight-normal" :read-only="false" :round-start-rating="false" :glow="5" glow-color="#ffd055"></star-rating>
            </div>
          </div>
          <textarea rows="3" placeholder="Leave a review here" class="w-100 p-2" style="background-color:#ffd055; border-radius: 20px; resize: none;"></textarea>
          <div>
            <p class="h5 mt-3">What was your experience like?</p>
          </div>
          <div class="row my-0 py-0  my-3">
            <div class="col-12 d-flex justify-content-center align-items-center">
              <star-rating text-class="h1 my-0 font-weight-normal" :read-only="false" :round-start-rating="false" :glow="5" glow-color="#ffd055"></star-rating>
            </div>
          </div>
          <textarea rows="3" placeholder="Leave a review here" class="w-100 p-2" style="background-color:#ffd055; border-radius: 20px; resize: none;"></textarea>
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
import StarRating from "vue-star-rating";

export default {
  name: "ReservationReview",
  props: ["id"],
  components: {StarRating},
  computed: {

  }
}
</script>

<style scoped>

</style>