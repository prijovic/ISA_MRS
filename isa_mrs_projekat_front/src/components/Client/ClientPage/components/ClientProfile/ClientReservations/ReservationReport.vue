<template>
  <div v-if="this.reports" class="modal fade" :id="'report-'+this.id" aria-labelledby="exampleModalLabel" aria-hidden="true">
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

<!--          <textarea id="complaint" :value="isReportPresent ? getReportComment : '' "-->
<!--                    :disabled="isReportPresent || isSaved"-->
<!--                    rows="3" placeholder="Leave a complaint here" class="w-100 p-2 px-3"-->
<!--                    style="background-color: #e23c52; border-radius: 20px; resize: none;" maxlength="1000"></textarea>-->

          <textarea v-if="isReportPresent" id="reportExists" :value="this.newReport.comment" disabled
                    rows="3" placeholder="Leave a complaint here" class="w-100 p-2 px-3"
                    style="background-color: #e23c52; border-radius: 20px; resize: none;" maxlength="1000"></textarea>

          <textarea v-if="!isReportPresent" :id="'insertReport'+this.resId" :content="this.newReport.comment"
                    :disabled="isSaved" rows="3" placeholder="Leave a complaint here" class="w-100 p-2 px-3"
                    style="background-color: #e23c52; border-radius: 20px; resize: none;" maxlength="1000"></textarea>

          <div v-if="complaintMissing" id="missingRentalGradeMessage" class="row text-center">
            <p class="h6 mt-3" style="color: #e23c52"><strong>You must enter a complaint.</strong></p>
          </div>
        </div>
        <div class="modal-footer d-flex" style="border-top: none; justify-content: space-evenly;">
          <button v-if="!isReportPresent" type="button" class="btn btn-secondary" data-bs-dismiss="modal"
                  style="background-color: #e23c52; color: white; width: 40%">{{ this.closeButtonTxt }}</button>
          <button :disabled="this.isSaved" v-if="!isReportPresent" type="button" class="btn btn-secondary"
                  style="color: white; width: 40%" @click="save">{{ this.saveButtonTxt }}
          </button>
          <button v-if="isReportPresent" type="button" class="btn btn-secondary w-100" data-bs-dismiss="modal"
                  style="color: white;">Close</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>


import axios from "axios";
import store from "@/store";

export default {
  name: "ReservationReport",
  props: ["id", "resId", "reports"],
  components: {},
  data() {
    return {
      complaintMissing: false,
      isSaved: false,
      saveButtonTxt: "Save",
      closeButtonTxt: "Cancel",
      newReport: {
        reservationId: this.resId,
        comment: null,
      },
    }
  },
  mounted() {
    this.newReport.comment =  this.getReportComment;
    // console.log(this.resId+".  "+this.newReport.comment);
  },
  methods: {
    save() {
      this.newReport.comment = document.getElementById("insertReport"+this.resId).value;
      if(!this.saveChecks()) this.saveReport();
    },
    saveChecks() {
      this.complaintMissing = false;
      this.newReport.comment = document.getElementById("insertReport"+this.resId).value;
      if(this.newReport.comment === "") this.complaintMissing = true;
      return this.complaintMissing;
    },
    saveReport() {
      axios.post("/Reservations/addReport", this.newReport, {
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
    isReportPresent() {
      for(let i=0; i<this.reports.length; i++)
        if(this.reports[i].user === "Client") return true;
      return false;
    },
    getReportComment() {
      let comment = "";
      for(let i=0; i<this.reports.length; i++)
        if(this.reports[i].user === "Client") comment = this.reports[i].comment;
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