<template>
  <div v-if="penalties" class="row">
    <div class="row p-0 m-0">
      <div v-if="clientHasPenalties" class="penalties">
        <div v-for="(penalty, i) in this.sortedPenalties" :key="i" class="penalty p-4 mb-3" style=" color: black;">
          <div class="row penalty-header" style="text-align: justify;">
            <div class="row d-flex justify-content-center align-items-center text-center mb-3">
              <img src="../../../../../Images/warn.png" style="height: 10vh; width: auto;" alt="">
            </div>
            <div class="row pe-5">
              <div class="row mb-3">
                <p class="h6">
                  You have received a penalty on <strong>{{ formatDate(penalty.timeStamp) }}</strong>
                  as a result of the complaint filed by <strong>{{ getComplaintAuthor(penalty) }}</strong>.
                </p>
              </div>
              <div v-if="!penalty.showedUp" class="row">
                <p class="h6">
                  Reason:
                </p>
                <p class="h6 mb-3">
                  <strong><i>You haven't showed up at your appointment.</i></strong>
                </p>
                <p v-if="ownerLeftAdditionalComment(penalty)" class="h6">
                  Owner has left additional comment:
                </p>
                <p v-if="ownerLeftAdditionalComment(penalty)" class="h6">
                  <strong><i>{{ penalty.comment }}</i></strong>
                </p>
              </div>
              <div v-else class="row">
                <p class="h6">
                  The Complaint is as follows:
                </p>
                <p class="h6">
                  <strong><i>{{ penalty.comment }}</i></strong>
                </p>
              </div>
            </div>

          </div>
          <div class="penalty-body" style="text-align: justify; color: black;">

          </div>
        </div>
      </div>
      <div v-else class="text-center">
        <label><i>You have no penalties.</i></label>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ClientPenalties",
  props: ["penalties"],
  data() {
    return {
      sortedPenalties: [],
    }
  },
  methods: {
    ownerLeftAdditionalComment(penalty) {
      if(penalty.comment === null || penalty.comment === '') return false;
      // console.log();
      return true;
    },
    getComplaintAuthor(penalty) {
      let author;
      if(penalty.author.userType === "Instructor") author = "instructor ";
      else  author = "owner ";
      return author + penalty.author.name + ' ' + penalty.author.surname;
    },
    formatDate(penaltyTime) {
      let date = new Date(penaltyTime);
      return date.getDate() + '.' + (date.getMonth() + 1) + '.' +  date.getFullYear() + '. at '
          + date.getHours() + ':' + ( (date.getMinutes()<10?'0':'') + date.getMinutes() );
    },
  },
  computed: {
    clientHasPenalties() {
      return this.penalties.length > 0;
    },
  },
  mounted() {
    this.sortedPenalties = this.penalties;
    this.sortedPenalties = this.sortedPenalties.sort((a, b) => new Date(b.timeStamp) > new Date(a.timeStamp) ? 1: -1);
  },
}
</script>

<style scoped>
div.penalty {
  border-radius: 40px;
  border: 2px solid darkgray;
}

div.penalty:hover {
  background-color:#e23c52;
}

div.penalty {
  background-color: white
}
</style>