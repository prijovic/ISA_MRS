<template>
  <div v-if="this.reviews" class="modal fade" :id="'review-'+this.id" aria-labelledby="exampleModalLabel" aria-hidden="true">
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
              <star-rating v-model="this.newReviews.ownerReview.grade"
                           text-class="h1 my-0 font-weight-normal" :round-start-rating="false"
                           :glow="5" glow-color="#ffd055" :star-size="50"
                           :read-only="isReviewPresent"
                           :rating="isReviewPresent ? getOwnerGrade : 0"
                           v-model:rating="this.newReviews.ownerReview.grade"></star-rating>
            </div>
            <div v-if="ownerGradeMissing" id="missingOwnerGradeMessage" class="row text-center">
              <p class="h6 mt-3" style="color: #e23c52"><strong>You must select a grade.</strong></p>
            </div>
          </div>
          <textarea id="ownerComment" :hidden="isReviewPresent ? !ownerCommentExists : false"
                    :value="isReviewPresent ? getOwnerComment : null " :disabled="isReviewPresent || isSaved"
                    rows="3" placeholder="Leave a review here" class="w-100 p-2 px-3"
                    style="background-color:#ffd055; border-radius: 20px; resize: none;" maxlength="1000" ></textarea>
          <div>
            <p class="h5 mt-3">What was your experience like?</p>
          </div>
          <div class="row my-0 py-0  my-3">
            <div class="col-12 d-flex justify-content-center align-items-center">
              <star-rating v-model="this.newReviews.rentalReview.grade"
                           text-class="h1 my-0 font-weight-normal" :round-start-rating="false"
                           :glow="5" glow-color="#ffd055" :star-size="50"
                           :read-only="isReviewPresent"
                           :rating="isReviewPresent ? getRentalGrade : 0"
                           v-model:rating="this.newReviews.rentalReview.grade"></star-rating>
            </div>
            <div v-if="rentalGradeMissing" id="missingRentalGradeMessage" class="row text-center">
              <p class="h6 mt-3" style="color: #e23c52"><strong>You must select a grade.</strong></p>
            </div>
          </div>
          <textarea id="rentalComment" :hidden="isReviewPresent ? !rentalCommentExists : false"
                    :value="isReviewPresent ? getRentalComment : '' " :disabled="isReviewPresent || isSaved"
                    rows="3" placeholder="Leave a review here" class="w-100 p-2 px-3"
                    style="background-color:#ffd055; border-radius: 20px; resize: none;" maxlength="1000"></textarea>
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
  name: "ReservationReview",
  props: ["id", "reviews", "resId"],
  components: {StarRating},
  data() {
    return {
      isSaved: false,
      saveButtonTxt: "Save",
      closeButtonTxt: "Cancel",
      ownerGradeMissing: false,
      rentalGradeMissing: false,
      newPlaceholder: "No comment.",
      newReviews: {
        reservationId: this.resId,
        ownerReview: {
          grade: null,
          comment: null,
        },
        rentalReview: {
          grade: null,
          comment: null,
        },
      },
    }
  },
  methods: {
    save() {
      this.saveCommentValues();
      if(!this.saveChecks()) this.saveReviews();
    },
    saveReviews() {
      axios.post("/Reservations/addReview", this.newReviews, {
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
    saveChecks() {
      this.ownerGradeMissing = false;
      this.rentalGradeMissing = false;
      if(this.newReviews.ownerReview.grade === null) this.ownerGradeMissing = true;
      if(this.newReviews.rentalReview.grade === null) this.rentalGradeMissing = true;
      return this.ownerGradeMissing || this.rentalGradeMissing;
    },
    saveCommentValues() {
      this.newReviews.ownerReview.comment = document.getElementById('ownerComment').value;
      this.newReviews.rentalReview.comment = document.getElementById('rentalComment').value;
    },
  },
  computed: {
    accessToken() {
      return store.state.access_token;
    },
    isReviewPresent() {
      for(let i=0; i<this.reviews.length; i++)
        if(this.reviews[i].reviewType === "OwnerReview" || this.reviews[i].reviewType === "RentalReview") return true;
      return false;
    },
    getOwnerGrade() {
      let grade = 0;
      for(let i=0; i<this.reviews.length; i++)
        if(this.reviews[i].reviewType === "OwnerReview") grade = this.reviews[i].grade;
      return grade;
    },
    getRentalGrade() {
      let grade = 0;
      for(let i=0; i<this.reviews.length; i++)
        if(this.reviews[i].reviewType === "RentalReview") grade = this.reviews[i].grade;
      return grade;
    },
    getOwnerComment() {
      let comment = "";
      for(let i=0; i<this.reviews.length; i++)
        if(this.reviews[i].reviewType === "OwnerReview") comment = this.reviews[i].comment;
      return comment;
    },
    getRentalComment() {
      let comment = "";
      for(let i=0; i<this.reviews.length; i++)
        if(this.reviews[i].reviewType === "RentalReview") comment = this.reviews[i].comment;
      return comment;
    },
    ownerCommentExists() {
      return !(this.getOwnerComment === "");
    },
    rentalCommentExists() {
      return !(this.getRentalComment === "");
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