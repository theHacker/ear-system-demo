<template>
    <div>
        <UContainer class="my-4">
            <UButton @click="sendRequest">
                Send demo request to add a quantity report
            </UButton>
        </UContainer>

        <UContainer class="my-4">
            Nothing to see here.
        </UContainer>
    </div>
</template>

<script setup lang="ts">
async function sendRequest() {
    await $fetch('/api/quantityReports', {
        method: 'POST',
        body: {
            "amount": "99.4",
            "unit": "TON",
            "yearMonth": "2012-04",
            "deviceTypeNumber": 123456,
            "registrationNumber": 987654,
            "declarationOfTruth": true
        },
    });
}
</script>
