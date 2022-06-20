<template>
  <div v-if="this.reviews" class="modal fade" :id="'review-'+this.resId" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
      <div class="modal-content">
        <div class="modal-header p-0 m-0" style="border-bottom: none">
        </div>
        <div class="modal-body pt-0 mt-0">
          <div class="row text-center pb-0 mb-0" style="border-bottom: 1px solid lightgray;">
            <h1 class="modal-title pb-1" style="color:#ffd055; font-weight: 700;">Reviews</h1>
          </div>
          <div>
            <p class="h5 mt-3">What did you think about the client?</p>
          </div>
          <div class="row my-0 py-0 my-3" >
            <div class="col-12 d-flex justify-content-center align-items-center">
              <star-rating text-class="h1 my-0 font-weight-normal" :round-start-rating="false"
                           :glow="5" glow-color="#ffd055" :star-size="50"
                           :read-only="isReviewPresent || isSaved"
                           :rating="getClientGrade"
                           v-model:rating="this.newReviews.clientReview.grade"></star-rating>
            </div>
            <div v-if="clientGradeMissing" id="missingGradeMessage" class="row text-center">
              <p class="h6 mt-3" style="color: #e23c52"><strong>You must select a grade.</strong></p>
            </div>
          </div>
          <div>
            <p class="h5 mt-3 mb-3">Write details and overview about this reservation:</p>
          </div>

          <textarea id="ownerCommentExists" v-if="isReviewPresent"
                    :value="this.newReviews.clientReview.comment" disabled
                    rows="3" class="w-100 p-2 px-3"
                    style="background-color:#ffd055; border-radius: 20px; resize: none;" maxlength="1000" ></textarea>

          <textarea :id="'insertReservationComment-'+this.resId" v-if="!isReviewPresent"
                    :content="this.newReviews.clientReview.comment" :disabled="isSaved"
                    rows="3" placeholder="Leave a review here" class="w-100 p-2 px-3"
                    style="background-color:#ffd055; border-radius: 20px; resize: none;" maxlength="1000" ></textarea>

          <div v-if="reservationCommentMissing" id="missingCommentMessage" class="row text-center">
            <p class="h6 mt-3" style="color: #e23c52"><strong>You must enter reservation overview.</strong></p>
          </div>
        </div>
        <div class="modal-footer d-flex" style="border-top: none; justify-content: space-evenly">
          <button v-if="!isReviewPresent" type="button" class="btn btn-secondary" data-bs-dismiss="modal"
                  style="background-color: #e23c52; color: white; width: 40%">{{ this.closeButtonTxt }}</button>
          <button :disabled="this.isSaved" v-if="!isReviewPresent" type="button" class="btn btn-secondary"
                  style="color: white; width: 40%" @click="save">{{ this.saveButtonTxt }}
          </button>
          <button v-if="isReviewPresent" type="button" class="btn btn-secondary w-100" data-bs-dismiss="modal"
                  style="color: white;">Close</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import StarRating from "vue-star-rating";

import store from "@/store";
import axios from "axios/index";

export default {
  name: "InstructorReservationReview",
  props: ["id", "reviews", "resId"],
  components: {StarRating},
  data() {
    return {
      isSaved: false,
      saveButtonTxt: "Save",
      closeButtonTxt: "Cancel",
      clientGradeMissing: false,
      reservationCommentMissing: false,
      // newPlaceholder: "No comment.",
      newReviews: {
        reservationId: this.resId,
        clientReview: {
          grade: null,
          comment: null,
        },
      },
    }
  },
  mounted() {
    this.newReviews.clientReview.comment = this.getComment;
  },
  methods: {
    save() {
      this.newReviews.clientReview.comment = document.getElementById("insertReservationComment-"+this.resId).value;
      if(!this.saveChecks()) this.saveReview();
    },
    saveChecks() {
      this.clientGradeMissing = false;
      this.reservationCommentMissing = false;
      if(this.newReviews.clientReview.grade === null) this.clientGradeMissing = true;
      if(this.newReviews.clientReview.comment === "") this.reservationCommentMissing = true;
      return this.clientGradeMissing || this.reservationCommentMissing;
    },
    saveReview() {
      axios.post("/Reservations/addInstructorReview", this.newReviews, {
        headers: {
          Authorization: "Bearer " + this.accessToken,
        }
      })
          .then(() => {
            this.saveButtonTxt = "Saved!";
            this.isSaved = true;
            this.closeButtonTxt = "Close";
          })
          .catch(error => {
            if (error.response.status === 404) {
              this.$notify({
                title: "Invalid Status",
                text: "Something went wrong. Try again later.",
                position: "bottom right",
                type: "warn"
              })
            } else if (error.response.status === 500) {
              this.$notify({
                title: "Internal Server Error",
                text: "Something went wrong on the server! Please try again later...",
                position: "bottom right",
                type: "error"
              })
            }
          })
    },
  },
  computed: {
    accessToken() {
      return store.state.access_token;
    },
    isReviewPresent() {
      for(let i=0; i<this.reviews.length; i++)
        if(this.reviews[i].reviewType === "ClientReview") return true;
      return false;
    },
    getClientGrade() {
      let grade = 0;
      for(let i=0; i<this.reviews.length; i++)
        if(this.reviews[i].reviewType === "ClientReview") grade = this.reviews[i].grade;
      return grade;
    },
    getComment() {
      let comment = "";
      for(let i=0; i<this.reviews.length; i++)
        if(this.reviews[i].reviewType === "ClientReview") comment = this.reviews[i].comment;
      return comment;
    },
  }
}
</script>

<style scoped>
textarea {
  color: white;
  font-style: italic;
  font-weight: 500;
  text-align: justify;
}

textarea::placeholder {
  color: white;
  font-weight: 400;
}

button:disabled {
  background-color: #008970;
}
</style>