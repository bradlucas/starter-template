-- ----------------------------------------------------------------------------------------------------
-- Account

-- :name all-accounts :? :*
-- :doc return all accounts
SELECT * FROM account

-- :name get-account :? :1
-- :doc return a specific account
SELECT * FROM account
where email = :email

