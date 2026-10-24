<template>
    <div>
        <UContainer class="my-4">
            <h1 class="text-4xl font-bold py-2">Quantity Reports</h1>

            <UTable
                :loading="loading"
                :data="quantityReports"
            />
        </UContainer>

        <UContainer class="my-8">
            <h2 class="text-xl font-bold py-2">Add demo quantity report</h2>

            <UButton @click="addQuantityReport">
                Send demo request to add a quantity report
            </UButton>
        </UContainer>
    </div>
</template>

<script setup lang="ts">
type QuantityReport = {
    amount: number;
    unit: 'KILOGRAM' | 'PIECE' | 'TON';
    yearMonth: string;
    deviceTypeNumber: number;
    registrationNumber: number;
    declarationOfTruth: boolean;
};

const loading = ref<boolean>(false);
const quantityReports = ref<QuantityReport[]>([]);

onMounted(() => {
    loadQuantityReports();
});

async function loadQuantityReports() {
    loading.value = true;

    try {
        quantityReports.value = await $fetch('/api/quantityReports');
    } catch (error) {
        console.error('Request failed:', error);
        // TODO error management
    } finally {
        loading.value = false;
    }
}

async function addQuantityReport() {
    try {
        await $fetch('/api/quantityReports', {
            method: 'POST',
            body: {
                'amount': 99.4,
                'unit': 'TON',
                'yearMonth': "2012-04",
                'deviceTypeNumber': 123456,
                'registrationNumber': 987654,
                'declarationOfTruth': true
            } satisfies QuantityReport,
        });
    } catch (error) {
        console.error('Request failed:', error);
        // TODO error management
    }
}
</script>
