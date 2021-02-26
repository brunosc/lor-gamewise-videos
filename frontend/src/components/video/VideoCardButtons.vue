<template>
  <div class="row">
    <div class="col">
      <a class="btn btn-secondary btn-sm w-100" :href="deckLink" target="_blank">
        Deck List
        <font-awesome-icon :icon="['fas', 'external-link-alt']" />
      </a>
    </div>
    <div class="col">
      <div v-if="codeCopied">
        <button type="button" class="btn btn-sm w-100 btn-outline-secondary" disabled>
          Copied
          <font-awesome-icon :icon="['fas', 'check']" />
        </button>
      </div>
      <div v-else>
        <input type="hidden" :id="deckCode" :value="deckCode">
        <button type="button" class="btn btn-sm w-100 btn-outline-secondary" @click="copyDeckCode">
          Deck Code
          <font-awesome-icon :icon="['far', 'copy']" />
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'VideoCardButtons',
  props: {
    deckCode: {
      type: String,
      required: true,
    }
  },

  data() {
    return {
      codeCopied: false,
    }
  },

  methods: {
    copyDeckCode() {
      const deckCodeInput = this.getDeckCodeInput();
      this.executeCopyCommand();
      this.clearCopyCommand(deckCodeInput);
    },

    getDeckCodeInput() {
      const deckCodeInput = document.querySelector('#' + this.deckCode);
      deckCodeInput.setAttribute('type', 'text');
      deckCodeInput.select();
      return deckCodeInput;
    },

    executeCopyCommand() {
      try {
        document.execCommand('copy');
        this.codeCopied = true;
        setTimeout(() => this.codeCopied = false, 3000);
      } catch (err) {
        console.log('Oops, unable to copy:');
        console.error(err);
      }
    },

    clearCopyCommand(deckCodeInput) {
      /* unselect the range */
      deckCodeInput.setAttribute('type', 'hidden');
      window.getSelection().removeAllRanges();
    }
  },

  computed: {
    deckLink() {
      return `https://lor.mobalytics.gg/decks/code/${this.deckCode}`;
    },
  }
}
</script>

<style scoped>

</style>