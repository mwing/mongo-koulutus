require 'rubygems'
require 'sinatra'
require File.join(File.dirname(__FILE__), "documents.rb")
MongoMapper.database = "3sq"

class MongoRestApp < Sinatra::Base
  get "/venues/list" do
    Venue.all({:fields => ["name"]}).to_json
  end

  get "/venues/search/:term" do |search_term|
    Venue.where(:name => /#{params[:term]}/i).all.to_json
  end

  get "/venues/near/:lat/:long" do |lat, long|
    hash = BSON::OrderedHash.new()
    hash["$near"] = [lat.to_f, long.to_f]
    hash["$maxDistance"] = 1
    q = BSON::OrderedHash.new().merge!({:location => hash})
    Venue.where(q).all.to_json
  end

  post "/checkin/user/:venue" do |venue|
    "{}"
  end

  get "/friends/near/:lat/:long" do |lat, long|
    "{}"
  end

  get "/users/list" do
    "{}"
  end

  get "/friends/list/:user" do |user|
    "{}"
  end

  get "/venue/tips/:venue" do |venue|
    venue
  end

  post "/venue/add_tip/:venue/" do |venue|
    Venue.where
  end

end
