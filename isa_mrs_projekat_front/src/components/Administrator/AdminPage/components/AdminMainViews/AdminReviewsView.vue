<template>
  <div class="row">
    <div class="col-1"></div>
    <div class="col-10 pt-5 mb-5">
      <div class="container-fluid pe-0 ps-0 me-0 ms-0">
        <div class="align-items-center">
          <div class="row main-col mb-1">
            <div class="d-flex justify-content-start p-0 m-0">
              <div class="me-2">
                <label for="UserType">Author type</label>
                <select class="form-control me-1" id="UserType" v-model="filterAuthor" style="max-width: 400px; min-width: fit-content; width: 25vh" @change="filterActive=false">
                  <option value="RentalObjectOwner" id="authorOwner">Owner</option>
                  <option value="Client" id="authorClient">Client</option>
                </select>
              </div>
              <div class="me-1">
                <label for="SubjectType">Subject type</label>
                <select class="form-control me-1" id="SubjectType" v-model="filterSubject" style="max-width: 400px; min-width: fit-content; width: 25vh" @change="filterActive=false">
                  <option v-if="filterAuthor!=='Client'" value="Client" id="subjectClient">Client</option>
                  <option v-if="filterAuthor!=='RentalObjectOwner'" value="RentalObjectOwner" id="subjectOwner">Owner</option>
                  <option v-if="filterAuthor!=='RentalObjectOwner'" value="RentalObject">Rental Object</option>
                </select>
              </div>
              <div class="mt-auto p-0">
                <button @click="filterButtonClicked" class="btn rounded" :class="filterActive?'btn-red':''" :disabled="filterSubject===null||filterAuthor===null"><font-awesome-icon :icon="filterActive?'x':'filter'"></font-awesome-icon></button>
              </div>
            </div>
          </div>
          <div class="row main-col text-center header rounded mb-3">
            <table>
              <thead>
              <tr>
                <th>Author</th>
                <th>Author type</th>
                <th>Comment</th>
                <th>Grade</th>
                <th>Subject</th>
                <th></th>
                <th></th>
              </tr>
              </thead>
              <tbody>
                <tr class="p-1" v-for="(review, index) in this.reviews" :key="index" :class="index%2!==0?'odd':'even'">
                  <td>
                    {{review.author.name + " " + review.author.surname}}
                  </td>
                  <td>
                    {{this.userRole(review.author)}}
                  </td>
                  <td>
                    {{review.comment}}
                  </td>
                  <td>
                    {{review.grade + " ★"}}
                  </td>
                  <td>
                    {{review.subjectName}}
                  </td>
                  <td>
                    <button class="btn" data-bs-toggle="modal" data-bs-target="#confirmationDialog">
                      <font-awesome-icon icon="check"></font-awesome-icon>
                    </button>
                    <div class="modal fade" id="confirmationDialog" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                      <div class="modal-dialog">
                        <div class="modal-content">
                          <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Review Management</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                          </div>
                          <div class="modal-body">
                            Do you want to accept {{review.author.name + " " + review.author.surname}}'s review for {{review.subjectName}}?
                          </div>
                          <div class="modal-footer">
                            <button type="button" class="btn btn-red" style="margin-right: 2vh;" data-bs-dismiss="modal">No</button>
                            <button type="button" class="btn" @click="manageReview(review.id, true)" data-bs-dismiss="modal">Yes</button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </td>
                  <td>
                    <button class="btn btn-red" data-bs-toggle="modal" data-bs-target="#confirmationDialogReject">
                      <font-awesome-icon icon="ban"></font-awesome-icon>
                    </button>
                    <div class="modal fade" id="confirmationDialogReject" tabindex="-1" aria-labelledby="RejectDialog" aria-hidden="true">
                      <div class="modal-dialog">
                        <div class="modal-content">
                          <div class="modal-header">
                            <h5 class="modal-title" id="RejectDialog">Review Management</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                          </div>
                          <div class="modal-body">
                            Do you want to reject {{review.author.name + " " + review.author.surname}}'s review for {{review.subjectName}}?
                          </div>
                          <div class="modal-footer">
                            <button type="button" class="btn btn-red" style="margin-right: 2vh;" data-bs-dismiss="modal">No</button>
                            <button type="button" class="btn" @click="manageReview(review.id, false)" data-bs-dismiss="modal">Yes</button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
            <nav aria-label="Page navigation">
              <ul class="pagination justify-content-center mt-3">
                <li class="page-item" v-if="totalPages > 1"><button class="page-link" :disabled="currentPage===0" @click="previousPage">Previous</button></li>
                <li class="page-item mt-auto me-1 ms-1" v-if="currentPage > 1 && totalPages > 3">...</li>
                <li class="page-item"><button class="page-link" :disabled="currentPage === 0" @click="numberedPage(1)">{{button1Content}}</button></li>
                <li class="page-item" v-if="totalPages > 1"><button class="page-link" :disabled="currentPage !== 0 && totalPages - currentPage > 1" @click="numberedPage(2)">{{button2Content}}</button></li>
                <li class="page-item" v-if="totalPages > 2"><button class="page-link" :disabled="totalPages - currentPage === 1" @click="numberedPage(3)">{{button3Content}}</button></li>
                <li class="page-item mt-auto me-1 ms-1" v-if="totalPages - currentPage > 1 && totalPages > 3 && button3Content !== totalPages">...</li>
                <li class="page-item" v-if="totalPages > 1"><button class="page-link" :disabled="totalPages - currentPage === 1" @click="nextPage">Next</button></li>
              </ul>
            </nav>
          </div>
        </div>
      </div>
      <div class="col-1"></div>
    </div>
  </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import {faFilter, faX, faCheck, faBan} from "@fortawesome/free-solid-svg-icons";
import axios from "axios";
import {useStore} from "vuex";
import {toggleProcessing} from "@/components/state";

library.add(faFilter, faX, faCheck, faBan);

export default {
  name: "AdminReviewsView",
  components: {FontAwesomeIcon},
  data() {
    return {
      reviews: [],
      currentPage: 0,
      totalPages: null,
      pageSize: 10,
      filterAuthor: null,
      filterSubject: null,
      filterActive: false,
    }
  },
  mounted() {
    const store = useStore();
    axios.get("/Reviews/getReviewsPage", {headers: {
        Authorization: "Bearer " + store.getters.access_token,
      },
      params: {
        page: this.currentPage,
        pageSize: this.pageSize
      }
    })
    .then(response => {
      this.reviews = response.data.content;
      this.currentPage = response.data.currentPage;
      this.totalPages = response.data.pages;
    })
    .catch(() =>{
      this.$notify({
        title: "Server error",
        text: "Server is currently off. Please try again later...",
        type: "error"
      });
    });
  },
  methods: {
    manageReview(reviewId, accepted) {
      toggleProcessing();
      axios.put("/Reviews/manageReview", null,{
        headers: {
          Authorization: "Bearer " + this.$store.getters.access_token,
        },
        params: {
         id: reviewId,
         accepted: accepted
        }
      })
      .then((response) => {
        this.$notify( {
          title: "Successfully managed",
          text: "You have successfully" + (accepted?"accepted":"rejected") + " the review with id " + response.data.id + ".",
          position: "bottom right",
          type: "success"
        });
        this.refreshPage();
        toggleProcessing();
      })
      .catch(() => {
        this.$notify({
          title: "Server error",
          text: "Server is currently off. Please try again later...",
          type: "error"
        });
        toggleProcessing();
      })
    },
    filterButtonClicked() {
      if (this.filterActive) {
        this.filterSubject = null;
        this.filterAuthor = null
        this.filterActive = !this.filterActive;
        this.refreshPage();
      } else if (this.filterSubject !== null && this.filterAuthor !== null) {
        this.filterActive = !this.filterActive;
        this.refreshPage();
      }
    },
    userRole(user) {
      if (user.userType === "Instructor") {
        return "Fishing Instructor";
      } else if (user.userType === "VacationRentalOwner") {
        return "House Owner";
      } else if (user.userType === "BoatOwner") {
        return "Boat Owner";
      } else if (user.userType === "SuperAdmin") {
        return "Supreme Admin";
      } else {
        return user.userType;
      }
    },
    previousPage() {
      this.currentPage -= 1;
      this.refreshPage();
    },
    nextPage() {
      this.currentPage += 1;
      this.refreshPage();
    },
    numberedPage(buttonNumber) {
      if (buttonNumber === 1) {
        this.currentPage = this.button1Content - 1;
      } else if (buttonNumber === 2) {
        this.currentPage = this.button2Content - 1;
      } else if (buttonNumber === 3) {
        this.currentPage = this.button3Content - 1;
      }
      this.refreshPage();
    },
    refreshPage() {
      let params;
      let url;
      if (this.filterSubject !== null && this.filterAuthor !== null) {
        params = {
          page: this.currentPage,
          pageSize: this.pageSize,
          author: this.filterAuthor,
          subject: this.filterSubject
        };
        url = "/Reviews/getReviewsFilterPage";
      } else {
        params = {
          page: this.currentPage,
          pageSize: this.pageSize
        };
        url = "/Reviews/getReviewsPage";
      }
      axios.get(url, {headers: {
          Authorization: "Bearer " + this.$store.getters.access_token,
        },
        params
      })
      .then(response => {
        this.reviews = response.data.content;
        this.currentPage = response.data.currentPage;
        this.totalPages = response.data.pages;
      })
      .catch(() =>{
        this.$notify({
          title: "Server error",
          text: "Server is currently off. Please try again later...",
          type: "error"
        });
      });
    }
  },
  computed: {
    button1Content() {
      return this.currentPage === 0 ? this.currentPage+1: this.totalPages - this.currentPage === 1 ? this.currentPage - 1 : this.currentPage;
    },
    button2Content() {
      return this.currentPage === 0 ? this.currentPage+2: this.totalPages - this.currentPage === 1 ? this.currentPage : this.currentPage+1;
    },
    button3Content() {
      return this.currentPage === 0 ? this.currentPage+3: this.totalPages - this.currentPage === 1 ? this.currentPage + 1 : this.currentPage+2;
    },
  }
}
</script>

<style scoped>
  label {
    color: #008970;
  }
</style>