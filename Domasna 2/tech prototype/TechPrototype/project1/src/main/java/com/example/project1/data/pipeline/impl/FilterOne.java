package com.example.project1.data.pipeline.impl;

import com.example.project1.data.pipeline.Filter;
import com.example.project1.model.StockEntity;
import com.example.project1.repository.StockRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class FilterOne implements Filter<List<StockEntity>> {

    private final StockRepository stockRepository;

    public FilterOne(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    private static final String STOCK_MARKET_URL = "https://www.mse.mk/mk/stats/symbolhistory/kmb";

    @Override
    public List<StockEntity> execute(List<StockEntity> input) throws IOException {
        Document document = Jsoup.connect(STOCK_MARKET_URL).get();
        Element selectMenu = document.select("select#Code").first();

        if (selectMenu != null) {
            Elements options = selectMenu.select("option");
            for (Element option : options) {
                String code = option.attr("value");
                if (!code.isEmpty() && code.matches("^[a-zA-Z]+$")) {
                    if (stockRepository.findByCompanyCode(code).isEmpty()) {
                        stockRepository.save(new StockEntity(code));
                    }
                }
            }
        }

        return stockRepository.findAll();
    }
}
