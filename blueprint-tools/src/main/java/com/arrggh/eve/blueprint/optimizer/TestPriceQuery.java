package com.arrggh.eve.blueprint.optimizer;

import com.arrggh.eve.blueprint.data.MarketPriceCache;
import com.arrggh.eve.blueprint.data.PriceQuery;
import com.arrggh.eve.blueprint.model.MarketPrice;

public class TestPriceQuery extends PriceQuery {
    public TestPriceQuery(MarketPriceCache priceCache) {
        super(priceCache);
    }

    @Override
    protected MarketPrice fetchPriceFromCrest(int typeId, int marketId) {
        throw new UnsupportedOperationException("Cannot fetch prices live from CREST in test typeId: " + typeId + " marketId: " + marketId);
    }
}
